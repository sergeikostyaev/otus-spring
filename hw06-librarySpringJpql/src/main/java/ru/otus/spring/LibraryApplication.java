package ru.otus.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) throws SQLException {
        Console.main(args);
        SpringApplication.run(LibraryApplication.class, args);
    }
}
