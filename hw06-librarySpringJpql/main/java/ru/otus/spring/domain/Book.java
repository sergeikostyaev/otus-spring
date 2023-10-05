package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
@Builder
@NamedEntityGraph(name = "lib-graph",
        attributeNodes = {@NamedAttributeNode("author"),@NamedAttributeNode("genre"), @NamedAttributeNode("comments") })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name")
    private String name;

    @JoinColumn(name = "author_id")
    @OneToOne(targetEntity = Author.class, cascade = CascadeType.PERSIST)
    private Author author;

    @JoinColumn(name = "genre_id")
    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.PERSIST)
    private Genre genre;


    @OneToMany(targetEntity = Comment.class, mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
