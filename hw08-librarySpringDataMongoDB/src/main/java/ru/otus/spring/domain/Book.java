package ru.otus.spring.domain;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Document(collection = "books")
public class Book {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

}



