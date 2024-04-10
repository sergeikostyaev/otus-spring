package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("comments")
@Getter
@Setter
public class Comment {

    @Id
    private Long id;

    private Long bookId;

    private String comment;

}

