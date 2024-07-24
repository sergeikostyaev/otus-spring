package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OzonRqDto {

    private String dir;
    private Filter filter;
    private int limit;
    private int offset;
    private With with;

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Filter {
        private String since;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class With {
        private boolean analytics_data;
        private boolean barcodes;
        private boolean financial_data;
        private boolean translit;
    }

}
