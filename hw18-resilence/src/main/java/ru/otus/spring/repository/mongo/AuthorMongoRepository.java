package ru.otus.spring.repository.mongo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.mongo.AuthorMongo;

@Repository
public interface AuthorMongoRepository extends CrudRepository<AuthorMongo, String> {

    AuthorMongo findBySystemId(Long id);

}