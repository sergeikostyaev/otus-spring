package ru.otus.spring.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.spring.common.MarketplaceCode;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wildberries_id")
    private String wildberriesId;

    @Column(name = "yandex_id")
    private String yandexId;

    @Column(name = "ozon_id")
    private String ozonId;

    @Column(name = "website_id")
    private String websiteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "marketplace_code")
    private MarketplaceCode marketplaceCode;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "region")
    private String region;
}
