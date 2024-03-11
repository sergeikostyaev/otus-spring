package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends R2dbcRepository<Comment, Long> {
    Flux<Comment> findCommentsByBookId(Long id);

}
