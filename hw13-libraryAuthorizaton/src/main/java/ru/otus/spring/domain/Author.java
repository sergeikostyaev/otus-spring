package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("authors")
@Getter
@Setter
public class Author {

    @Id
    private Long id;

    private String authorName;

}
