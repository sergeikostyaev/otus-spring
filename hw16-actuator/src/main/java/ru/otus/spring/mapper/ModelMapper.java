package ru.otus.spring.mapper;

public interface ModelMapper<M, D> {

    D toDto(M model);

}
