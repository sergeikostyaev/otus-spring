package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

public interface CommentRepository {

    Comment findById(Long id);

}
