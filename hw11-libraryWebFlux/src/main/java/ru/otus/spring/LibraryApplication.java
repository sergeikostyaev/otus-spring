package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        System.out.println("http://localhost:8080");
        SpringApplication.run(LibraryApplication.class, args);
    }
}
