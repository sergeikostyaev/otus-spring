package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String name;

    @JoinColumn(name = "author_id")
    @OneToOne
    private Author author;

    @JoinColumn(name = "genre_id")
    @OneToOne
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "book")
    private List<Comment> comments;

}



