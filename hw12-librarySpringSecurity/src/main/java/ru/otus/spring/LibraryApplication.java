package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Admin login/password: admin/password
//User login/password: user/password
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        //Console.main(args);
        System.out.println("http://localhost:8080");
        SpringApplication.run(LibraryApplication.class, args);
    }
}
