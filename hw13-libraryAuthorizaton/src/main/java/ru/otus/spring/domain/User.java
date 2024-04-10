package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("users")
@Getter
@Setter
public class User {
    @Id
    private Long id;

    private String userLogin;

    private String userPassword;

    private String userRole;

}