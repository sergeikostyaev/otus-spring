package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.mongo.BookMongo;

import java.util.List;

@Repository
public interface BookMongoRepository extends CrudRepository<BookMongo, Long> {
    List<BookMongo> findByName(String name);

    List<BookMongo> deleteBookByName(String name);

}
