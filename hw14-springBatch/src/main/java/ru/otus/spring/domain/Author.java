package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "author")
    private List<Book> books;

}
