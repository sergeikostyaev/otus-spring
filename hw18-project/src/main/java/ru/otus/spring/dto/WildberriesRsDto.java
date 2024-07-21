package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WildberriesRsDto {

    private LocalDateTime date;

    private String subject;

    private String brand;

    private String nmId;

    private String supplierArticle;

    private String countryName;

    private String oblastOkrugName;

    private String regionName;

    private String gNumber;

    private String srid;

    @Setter
    private Integer number = 1;

    @Setter
    private String identifier;

    @Setter
    private Long finishedPrice;

}
