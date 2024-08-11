package ru.otus.spring.domain.mongo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Document(collection = "books")
public class BookMongo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id = UUID.randomUUID().toString();

    @Indexed(unique=true)
    private Long systemId;

    private String name;

    private Long genreId;

    private Long authorId;

}



