package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.repository.BookMongoRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.stream.StreamSupport;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final BookMongoRepository bookMongoRepository;

    private final BookRepository bookRepository;

    private final Job importUserJob;

    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startMigration", key = "sm")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(importUserJob, new JobParametersBuilder()
                .toJobParameters());
        System.out.println(execution);
    }

    @ShellMethod(key = {"getAllBooks", "gab"})
    public void getAllBooks() {
        var books = bookRepository.findAll();
        if (!books.isEmpty()) {
            books.forEach(book -> System.out.printf("Id %s:\nName: %s\nAuthor: %s\nGenre: %s\nComments:",
                    book.getId(), book.getName(), book.getGenre().getName(), book.getAuthor().getName()));
        } else {
            System.out.print("No books found");
        }
    }

    @ShellMethod(key = {"getAllBooks", "gabm"})
    public void getAllMongoBooks() {
        var books = StreamSupport.stream(bookMongoRepository.findAll().spliterator(), false).toList();
        if (!books.isEmpty()) {
            books.forEach(book -> System.out.printf("Id %s:\nName: %s\nAuthor: %s\nGenre: %s\nComments:",
                    book.getId(), book.getName(), book.getGenre().getName(), book.getAuthor().getName()));
        } else {
            System.out.print("No books found");
        }
    }

}
