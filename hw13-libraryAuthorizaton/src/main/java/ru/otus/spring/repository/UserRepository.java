package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.User;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<User> findFirstByUserLogin(String login);

}
