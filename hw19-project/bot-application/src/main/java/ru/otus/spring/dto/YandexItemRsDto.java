package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class YandexItemRsDto {

    private String status;

    private Result result;

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private List<OfferMapping> offerMappings;

        @Getter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OfferMapping {
            private Offer offer;
            private Mapping mapping;

            @Getter
            @ToString
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Offer {
                private String category;
                private String vendor;
                private String vendorCode;

            }

            @Getter
            @ToString
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Mapping {
                private long marketModelId;
            }
        }
    }
}
