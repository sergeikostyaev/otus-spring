package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AuthorDto {

    private Long id;

    private String name;
}
