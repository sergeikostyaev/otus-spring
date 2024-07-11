package ru.otus.spring.mapper;

public interface EntityMapper<I, O> {

    O map(I model);

}
