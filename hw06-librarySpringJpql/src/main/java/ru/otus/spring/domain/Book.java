package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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
    @OneToOne(targetEntity = Author.class, cascade = CascadeType.DETACH)
    private Author author;

    @JoinColumn(name = "genre_id")
    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.DETACH)
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments;

}



