package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

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

    public static BookDto toDto(Book model) {
        return BookDto.builder()
                .id(model.getId())
                .name(model.getName())
                .genre(GenreDto.builder()
                        .id(model.getGenre().getId())
                        .name(model.getGenre().getName())
                        .build())
                .author(AuthorDto.builder()
                        .id(model.getAuthor().getId())
                        .name(model.getAuthor().getName())
                        .build())
                .comments(model.getComments().stream().map(m -> {
                    return CommentDto.builder()
                            .id(m.getId())
                            .comment(m.getComment())
                            .build();
                }).collect(Collectors.toList()))
                .build();
    }
}



