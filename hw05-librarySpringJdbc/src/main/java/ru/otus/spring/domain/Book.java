package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {

    private final Long id;

    private final String name;

    private final Author author;

    private final Genre genre;
}
