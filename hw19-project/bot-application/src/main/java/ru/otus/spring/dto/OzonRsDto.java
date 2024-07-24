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
public class OzonRsDto {

    private List<Order> result;

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private long order_id;
        private String order_number;
        private String posting_number;
        private String created_at;
        private String in_process_at;
        private List<Product> products;
        private AnalyticsData analytics_data;

        @Getter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Product {
            private long sku;
            private String name;
            private int quantity;
            private String offer_id;
            private String price;
        }

        @Getter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AnalyticsData {
            private String region;
            private String city;
        }

    }
}

