package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.entity.Purchase;
import ru.otus.spring.dao.repository.PurchaseRepository;
import ru.otus.spring.dto.WildberriesRsDto;
import ru.otus.spring.service.WildberriesService;
import ru.otus.spring.webclient.WildberriesWebClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ru.otus.spring.common.MarketplaceCode.WILDBERRIES;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class WildberriesServiceImpl implements WildberriesService {

    private final WildberriesWebClientService wildberriesWebClientService;

    private final PurchaseRepository purchaseRepository;

    private final AtomicReference<Integer> orderNumber = new AtomicReference<>(1);

    private final Map<String, WildberriesRsDto> orderBuffer = new HashMap<>();

    private static final Map<Long, String> NAME_MAP = new HashMap<>() {{
        put(450714L, "Прокладки ежедневные Сurved (анатомические) 30шт");
        put(450712L, "Прокладки ежедневные удлиненные Long 16шт");
        put(450713L, "Прокладки ежедневные mini 30шт");
        put(450715L, "Прокладки ежедневные tanga (для стрингов) 30шт");
        put(450716L, "Прокладки ежедневные Ultra (ультратонкие) 22шт");
        put(450711L, "Прокладки ежедневные normal 18шт");
        put(450703L, "Ночные прокладки длинные maxi night 4 капли 10шт");
        put(450702L, "Прокладки гигиенические maxi super 3 капли 12шт");
        put(450701L, "Прокладки гигиенические maxi regular 2 капли 14шт");
        put(450707L, "Прокладки гигиенические длинные с крылышками ultra long 10шт");
        put(450704L, "Прокладки гигиенические с крылышками ultra regular 14шт");
        put(450705L, "Прокладки гигиенические с крылышками ultra super 12шт");
        put(450706L, "Прокладки гигиенические с крылышками ultra super plus 12шт");
        put(450709L, "Прокладки гигиенические длинные с крылышками extra long 8шт");
        put(450710L, "Прокладки гигиенические с крылышками extra regular 12шт");
        put(450708L, "Прокладки гигиенические с крылышками extra super 10шт");
    }};

    @Override
    public void initWildberriesProcessing(ArrayBlockingQueue<String> messageQueue) {

        ScheduledExecutorService wildberriesClientExecutorService = Executors.newSingleThreadScheduledExecutor();
        wildberriesClientExecutorService.scheduleAtFixedRate(new Thread(() -> {
            Thread.currentThread().setName("Wildberries service thread");
            log.info("Registered thread: {}", Thread.currentThread().getName());


            List<WildberriesRsDto> boughtItems = wildberriesWebClientService.call();
            if (Objects.nonNull(boughtItems) && !boughtItems.isEmpty()) {
                log.info("Called wildberries service with last return: {}", boughtItems.get(boughtItems.size() - 1));

                boughtItems = boughtItems.stream().peek(item -> {
                    String srid = item.getSrid();
                    String identifier = srid.contains(".") ? srid.substring(0, srid.lastIndexOf('.', srid.lastIndexOf('.') - 1)) : srid;
                    item.setIdentifier(identifier);
                }).toList();

                Map<String, Long> count = boughtItems.stream()
                        .collect(Collectors.groupingBy(item -> item.getIdentifier() + item.getSupplierArticle(), Collectors.counting()));

                var processedItems = boughtItems.stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toMap(
                                        item -> item.getIdentifier() + item.getSupplierArticle(),
                                        item -> item,
                                        (existing, replacement) -> existing
                                ),
                                map -> map.values().stream()
                                        .peek(item -> {
                                            int number = Math.toIntExact(count.get(item.getIdentifier() + item.getSupplierArticle()));
                                            item.setNumber(number);
                                            item.setFinishedPrice(item.getFinishedPrice() * number);
                                        })
                                        .sorted(Comparator.comparing(WildberriesRsDto::getDate))
                                        .collect(Collectors.toList())
                        ));

                processedItems.stream().filter(item -> !orderBuffer.containsKey(item.getSrid())).forEach(item -> {
                    messageQueue.add(mapWildberriesResponseItem(orderNumber.get(), item));
                    orderBuffer.put(item.getSrid(), item);
                    orderNumber.getAndSet(orderNumber.get() + 1);
                    savePurchase(item);
                });

            } else {
                log.info("Called Wildberries service null or empty");
            }


            orderBuffer.entrySet().removeIf(entry -> {
                LocalDateTime orderDate = entry.getValue().getDate();
                return orderDate.isBefore(LocalDateTime.now().minusDays(1));
            });

        }), 5, 70, TimeUnit.SECONDS);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void processOrderNumber() {
        orderNumber.set(1);
    }

    @Override
    public Integer countPeriodIncome(LocalDate period) {
        var boughtItems = wildberriesWebClientService.call(period);

        int totalSum = 0;

        if (Objects.nonNull(boughtItems) && !boughtItems.isEmpty()) {
            for (WildberriesRsDto item : boughtItems) {
                totalSum += item.getFinishedPrice();
            }
        }

        log.info("Counted wildberries income for period: {}", period);

        return totalSum;
    }

    @Override
    public Integer getBufferCount() {
        return orderBuffer.size();
    }

    private String mapWildberriesResponseItem(Integer orderNumber, WildberriesRsDto item) {
        StringBuilder result = new StringBuilder(orderNumber + " заказ на Wildberries от " +
                item.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
        result.append("Время: " + item.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n\n");

        String link = "https://www.wildberries.ru/catalog/" + item.getNmId() + "/detail.aspx?targetUrl=GP";

        result.append("Бренд: " + item.getBrand() + "\n");
        result.append("Тип: " + item.getSubject() + "\n");
        result.append("Артикул Wildberries: " + item.getNmId() + "\n\n");
        result.append("Товар: [" + NAME_MAP.get(Long.valueOf(item.getSupplierArticle())) + "](" + link + ")\n");
        result.append("Количество: " + item.getNumber() + "\n\n");
        result.append("Страна: " + capitalizeFirstLetter(item.getCountryName()) + "\n");
        result.append("Округ: " + capitalizeFirstLetter(item.getOblastOkrugName()) + "\n");
        result.append("Регион: " + capitalizeFirstLetter(item.getRegionName()) + "\n\n");
        result.append("Конечная цена: " + item.getFinishedPrice() + " руб\n");

        return result.toString();
    }

    private String capitalizeFirstLetter(String line) {
        if (line == null || line.isEmpty()) {
            return line;
        }

        char firstLetter = Character.toUpperCase(line.charAt(0));
        String remainingString = line.substring(1);
        return firstLetter + remainingString;
    }

    private void savePurchase(WildberriesRsDto item) {
        try {
            for (int i = 0; i < item.getNumber(); i++) {
                purchaseRepository.save(Purchase.builder()
                        .wildberriesId(item.getNmId())
                        .marketplaceCode(WILDBERRIES)
                        .date(LocalDate.now())
                        .region(capitalizeFirstLetter(item.getRegionName()))
                        .build());
            }
        } catch (Exception e) {
            log.info("Error saving purchase in WildberriesService");
        }
    }
}