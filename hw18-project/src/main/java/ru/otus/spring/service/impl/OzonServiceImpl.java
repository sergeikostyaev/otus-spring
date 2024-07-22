package ru.otus.spring.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.entity.Purchase;
import ru.otus.spring.dao.repository.PurchaseRepository;
import ru.otus.spring.dto.OzonRsDto;
import ru.otus.spring.service.OzonService;
import ru.otus.spring.webclient.OzonWebClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static ru.otus.spring.common.MarketplaceCode.OZON;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class OzonServiceImpl implements OzonService {

    private final OzonWebClientService ozonWebClientService;

    private final PurchaseRepository purchaseRepository;

    private final AtomicReference<Integer> orderNumber = new AtomicReference<>(1);

    private final Map<String, OzonRsDto.Order> orderBuffer = new HashMap<>();

    @Override
    public void initOzonProcessing(ArrayBlockingQueue<String> messageQueue) {

        ScheduledExecutorService yandexClientExecutorService = Executors.newSingleThreadScheduledExecutor();
        yandexClientExecutorService.scheduleAtFixedRate(new Thread(() -> {
            Thread.currentThread().setName("Ozon service thread");
            log.info("Registered thread: {}", Thread.currentThread().getName());

            OzonRsDto response = ozonWebClientService.call(LocalDate.now());
            boolean responseIsRelevant = Objects.nonNull(response) && Objects.nonNull(response.getResult()) && !response.getResult().isEmpty();
            log.info("Called Ozon service. Is relevant: {}", responseIsRelevant);

            if (responseIsRelevant) {
                List<OzonRsDto.Order> orders = response.getResult();

                orders.stream().filter(order -> !orderBuffer.containsKey(order.getPosting_number()))
                        .sorted(Comparator.comparing(item -> parseDate(item.getIn_process_at())))
                        .forEach(order -> {
                            messageQueue.add(mapOzonResponseItem(orderNumber.get(), order));
                            orderBuffer.put(order.getPosting_number(), order);
                            orderNumber.getAndSet(orderNumber.get() + 1);
                            try {
                                order.getProducts().forEach(product -> purchaseRepository.save(Purchase.builder()
                                        .ozonId(String.valueOf(product.getSku()))
                                        .marketplaceCode(OZON)
                                        .date(LocalDate.now())
                                        .name(product.getName())
                                        .region(order.getAnalytics_data().getRegion())
                                        .build()));
                            } catch (Exception e) {
                                log.info("Error saving purchase in OzonService");
                            }
                        });
            }

            orderBuffer.entrySet().removeIf(entry -> {
                LocalDateTime orderDate = parseDate(entry.getValue().getCreated_at());
                return orderDate.isBefore(LocalDateTime.now().minusDays(3));
            });


        }), 5, 70, TimeUnit.SECONDS);

    }

    @Scheduled(cron = "0 0 0 * * *")
    private void processOrderNumber() {
        orderNumber.set(1);
    }

    @Override
    public Integer countPeriodIncome(LocalDate period) {
        var ozonOrders = ozonWebClientService.call(period);

        AtomicInteger totalSum = new AtomicInteger();

        if (Objects.nonNull(ozonOrders) && !ozonOrders.getResult().isEmpty()) {
            ozonOrders.getResult().forEach(order -> order.getProducts().forEach(item -> {
                totalSum.addAndGet(((int) (item.getQuantity() * Double.parseDouble(item.getPrice()))));
            }));
        }

        log.info("Counted yandex income for period: {}", period);

        return totalSum.get();
    }

    @Override
    public Integer getBufferCount() {
        return orderBuffer.size();
    }

    private String mapOzonResponseItem(Integer orderNumber, OzonRsDto.Order order) {
        LocalDate date = LocalDate.parse(order.getCreated_at().substring(0, order.getCreated_at().indexOf('T')), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);


        StringBuilder result = new StringBuilder(orderNumber + " заказ на Ozon от " + formattedDate + "\n");
        result.append("Время: " + order.getCreated_at().substring(order.getCreated_at().indexOf('T') + 1, order.getCreated_at().indexOf('.')) + "\n\n");

        String link = "https://www.ozon.ru/product/";
        AtomicInteger price = new AtomicInteger();

        order.getProducts().forEach(item -> {

            String productId = String.valueOf(item.getSku());

            result.append("Артикул Ozon: " + productId + "\n\n");

            price.addAndGet((int) Double.parseDouble(item.getPrice()));


            result.append("Товар: [" + item.getName() + "](" + link + productId + ")");
            result.append("\nКоличество: " + item.getQuantity() + "\n\n");

        });

        result.append("Регион: " + order.getAnalytics_data().getRegion() + "\n");
        result.append("Город: " + order.getAnalytics_data().getCity() + "\n\n");
        result.append("Конечная цена: " + price + " руб\n");


        return result.toString();
    }

    private LocalDateTime parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = dateTime.format(outputFormatter);

        return LocalDateTime.parse(formattedDateTime);
    }
}
