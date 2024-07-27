package ru.otus.spring.repository.mongo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.mongo.GenreMongo;

@Repository
public interface GenreMongoRepository extends CrudRepository<GenreMongo, String> {

    GenreMongo findBySystemId(Long id);

}
