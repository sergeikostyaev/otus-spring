package ru.otus.spring.repository.mongo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.mongo.BookMongo;

@Repository
public interface BookMongoRepository extends CrudRepository<BookMongo, String> {

    BookMongo findBySystemId(Long systemId);

}
