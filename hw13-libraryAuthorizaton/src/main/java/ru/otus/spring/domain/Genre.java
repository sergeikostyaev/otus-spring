package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("genres")
@Getter
@Setter
public class Genre {

    @Id
    private Long id;

    private String genreName;

}
