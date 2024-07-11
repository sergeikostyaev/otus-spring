package ru.otus.spring.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Document(collection = "books")
public class BookMongo {

    @Id
    private final String id = UUID.randomUUID().toString();

    private String name;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

}



