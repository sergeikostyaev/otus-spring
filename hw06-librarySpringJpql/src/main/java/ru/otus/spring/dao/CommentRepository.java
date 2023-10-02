package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment findById(Long id);

    List<Comment> findAllByBookId(Long id);

}
