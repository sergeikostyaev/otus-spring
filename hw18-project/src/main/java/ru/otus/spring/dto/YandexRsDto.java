package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class YandexRsDto {

    private List<Order> orders;

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private Long id;
        private String creationDate;
        private Integer buyerTotal;
        private List<Item> items;
        private Delivery delivery;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private String offerId;
        private String offerName;
        private Integer count;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Delivery {
        private Region region;
    }


    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Region {
        private Integer id;
        private String name;
        private String type;
        private Region parent;
    }

}
