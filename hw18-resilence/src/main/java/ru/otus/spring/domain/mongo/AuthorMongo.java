package ru.otus.spring.domain.mongo;

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
@Document(collection = "authors")
public class AuthorMongo {

    @Id
    private String id = UUID.randomUUID().toString();

    @Indexed(unique=true)
    private Long systemId;

    private String name;

}
