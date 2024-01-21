package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        System.out.println("http://localhost:8080");
        SpringApplication.run(LibraryApplication.class, args);
    }
}
