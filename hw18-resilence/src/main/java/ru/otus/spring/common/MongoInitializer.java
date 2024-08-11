package ru.otus.spring.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.mongo.AuthorMongo;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.GenreMongo;
import ru.otus.spring.repository.mongo.AuthorMongoRepository;
import ru.otus.spring.repository.mongo.BookMongoRepository;
import ru.otus.spring.repository.mongo.GenreMongoRepository;

@Component
@RequiredArgsConstructor
public class MongoInitializer implements CommandLineRunner {

    private final BookMongoRepository bookMongoRepository;

    private final AuthorMongoRepository authorMongoRepository;

    private final GenreMongoRepository genreMongoRepository;

    @Override
    public void run(String... args) throws Exception {
        bookMongoRepository.save(BookMongo.builder()
                .name("Жизнь двенадцати цезарей")
                .systemId(1L)
                .build());

        bookMongoRepository.save(BookMongo.builder()
                .name("Белая гвардия")
                .systemId(2L)
                .build());

        bookMongoRepository.save(BookMongo.builder()
                .name("Собачье сердце")
                .systemId(3L)
                .build());

        bookMongoRepository.save(BookMongo.builder()
                .name("Игрок")
                .systemId(4L)
                .build());

        authorMongoRepository.save(AuthorMongo.builder()
                .name("Светоний")
                .systemId(1L)
                .build());

        authorMongoRepository.save(AuthorMongo.builder()
                .name("Светоний")
                .systemId(2L)
                .build());

        authorMongoRepository.save(AuthorMongo.builder()
                .name("Светоний")
                .systemId(3L)
                .build());

        genreMongoRepository.save(GenreMongo.builder()
                        .name("Исторические")
                        .systemId(1L)
                .build());

        genreMongoRepository.save(GenreMongo.builder()
                .name("Драма")
                .systemId(2L)
                .build());

    }
}
