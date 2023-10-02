package ru.otus.spring.dto;

import lombok.Data;
@Data
public class CommentDto {

    private Long id;

    private BookDto book;

    private String comment;
}
