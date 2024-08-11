package ru.otus.spring.repository.mongo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.mongo.CommentMongo;

import java.util.List;

@Repository
public interface CommentMongoRepository extends CrudRepository<CommentMongo, String> {

    CommentMongo findBySystemId(Long id);

    List<CommentMongo> findByBookId(Long id);

}
