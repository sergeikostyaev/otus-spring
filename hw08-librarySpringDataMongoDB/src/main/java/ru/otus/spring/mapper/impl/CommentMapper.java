package ru.otus.spring.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.mapper.ModelMapper;

@Component
public class CommentMapper implements ModelMapper<Comment, CommentDto> {
    @Override
    public CommentDto toDto(Comment model) {
        return CommentDto.builder()
                .comment(model.getComment())
                .build();
    }
}
