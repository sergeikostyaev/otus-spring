package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class LibraryApplication {

    public static void main(String[] args) {
        System.out.println("http://localhost:8080");
        SpringApplication.run(LibraryApplication.class, args);
    }
}
