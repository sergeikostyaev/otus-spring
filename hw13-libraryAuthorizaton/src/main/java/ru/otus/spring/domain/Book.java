package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
@Getter
@Setter
@AllArgsConstructor
public class Book {

    @Id
    private Long id;

    private String bookName;

    private Long authorId;

    private Long genreId;

}



