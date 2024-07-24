package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.entity.Purchase;
import ru.otus.spring.dao.repository.PurchaseRepository;
import ru.otus.spring.dto.YandexItemRsDto;
import ru.otus.spring.dto.YandexRsDto;
import ru.otus.spring.service.YandexService;
import ru.otus.spring.webclient.YandexWebClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static ru.otus.spring.common.MarketplaceCode.YANDEX;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class YandexServiceImpl implements YandexService {

    private final YandexWebClientService yandexWebClientService;

    private final PurchaseRepository purchaseRepository;

    private final AtomicReference<Integer> orderNumber = new AtomicReference<>(1);

    private final Map<Long, YandexRsDto.Order> orderBuffer = new HashMap<>();

    @Override
    public void initYandexProcessing(ArrayBlockingQueue<String> messageQueue) {

        ScheduledExecutorService yandexClientExecutorService = Executors.newSingleThreadScheduledExecutor();
        yandexClientExecutorService.scheduleAtFixedRate(new Thread(() -> {
            Thread.currentThread().setName("Yandex service thread");
            log.info("Registered thread: {}", Thread.currentThread().getName());


            YandexRsDto response = yandexWebClientService.call();
            boolean responseIsRelevant = Objects.nonNull(response) && Objects.nonNull(response.getOrders()) && !response.getOrders().isEmpty();
            log.info("Called Yandex service. Is relevant: {}", responseIsRelevant);

            if (responseIsRelevant) {
                List<YandexRsDto.Order> orders = response.getOrders();

                orders.stream().filter(order -> !orderBuffer.containsKey(order.getId()))
                        .sorted(Comparator.comparing(item -> LocalDateTime.parse(item.getCreationDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))))
                        .forEach(order -> {
                            messageQueue.add(mapYandexResponseItemAndSavePurchase(orderNumber.get(), order));
                            orderBuffer.put(order.getId(), order);
                            orderNumber.getAndSet(orderNumber.get() + 1);
                        });

            }

            orderBuffer.entrySet().removeIf(entry -> {
                LocalDateTime orderDate = LocalDateTime.parse(entry.getValue().getCreationDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
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
        var yandexOrders = yandexWebClientService.call(period);

        int totalSum = 0;

        if (Objects.nonNull(yandexOrders) && !yandexOrders.getOrders().isEmpty()) {
            for (YandexRsDto.Order order : yandexOrders.getOrders()) {
                totalSum += order.getBuyerTotal();
            }
        }

        log.info("Counted yandex income for period: {}", period);

        return totalSum;
    }

    @Override
    public Integer getBufferCount() {
        return orderBuffer.size();
    }

    private String mapYandexResponseItemAndSavePurchase(Integer orderNumber, YandexRsDto.Order order) {
        StringBuilder result = new StringBuilder(orderNumber + " заказ на YandexMarket от " +
                order.getCreationDate().substring(0, order.getCreationDate().indexOf(' ')) + "\n");
        result.append("Время: " + order.getCreationDate().substring(order.getCreationDate().indexOf(' '), order.getCreationDate().length()) + "\n\n");

        String link = "https://market.yandex.ru/product/";

        String country = "";
        String countryDistrict = "";
        String republic = "";

        YandexRsDto.Region regionPart = order.getDelivery().getRegion();

        while (Objects.nonNull(regionPart)) {

            switch (regionPart.getType()) {
                case "COUNTRY": {
                    country = regionPart.getName();
                    regionPart = regionPart.getParent();
                    break;
                }
                case "COUNTRY_DISTRICT": {
                    countryDistrict = regionPart.getName();
                    regionPart = regionPart.getParent();
                    break;
                }
                case "REPUBLIC": {
                    republic = regionPart.getName();
                    regionPart = regionPart.getParent();
                    break;
                }
                default: {
                    regionPart = regionPart.getParent();
                    break;
                }
            }
        }

        StringBuilder items = new StringBuilder();
        String finalRepublic = republic;
        order.getItems().forEach(item -> {

            YandexItemRsDto itemInfo = yandexWebClientService.call(item.getOfferId());

            result.append("Бренд: " + itemInfo.getResult().getOfferMappings().get(0).getOffer().getVendor() + "\n");
            result.append("Тип: " + itemInfo.getResult().getOfferMappings().get(0).getOffer().getCategory() + "\n");
            result.append("Артикул YandexMarket: " + itemInfo.getResult().getOfferMappings().get(0).getMapping().getMarketModelId() + "\n\n");

            String productId = String.valueOf(itemInfo.getResult().getOfferMappings().get(0).getMapping().getMarketModelId());


            result.append("Товар: [" + item.getOfferName() + "](" + link + productId + ")");
            result.append("\nКоличество: " + item.getCount() + "\n\n");

            savePurchase(itemInfo, item.getCount(), item.getOfferName(), finalRepublic);
        });


        result.append("Страна: " + country + "\n");
        result.append("Округ: " + countryDistrict + "\n");
        result.append("Регион: " + republic + "\n\n");
        result.append("Конечная цена: " + order.getBuyerTotal() + " руб\n");


        return result.toString();
    }

    private void savePurchase(YandexItemRsDto itemInfo, Integer count, String name, String region) {
        try {
            for (int i = 0; i < count; i++) {
                purchaseRepository.save(Purchase.builder()
                        .yandexId(String.valueOf(itemInfo.getResult().getOfferMappings().get(0).getMapping().getMarketModelId()))
                        .marketplaceCode(YANDEX)
                        .date(LocalDateTime.now())
                        .name(name)
                        .region(region)
                        .build());
            }
        } catch (Exception e) {
            log.info("Error saving purchase in YandexService");
        }
    }
}

