package ru.otus.spring.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByLogin(String login);

}
