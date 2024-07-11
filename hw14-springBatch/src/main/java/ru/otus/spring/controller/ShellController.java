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

    private final Job migrateBooksJob;

    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startMigration", key = "sm")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(migrateBooksJob, new JobParametersBuilder()
                .toJobParameters());
        System.out.println(execution);
    }

    @ShellMethod(key = {"getAllBooks", "gab"})
    public String getAllBooks() {
        var books = bookRepository.findAll();

        StringBuilder result = new StringBuilder();

        if (!books.isEmpty()) {
            books.forEach(book -> result.append(String.format("Id %s:\nName: %s\nAuthor: %s\nGenre: %s\nComments: %s\n",
                    book.getId(), book.getName(), book.getGenre().getName(), book.getAuthor().getName(), book.getComments())));
        } else {
            result.append("No books found");
        }

        return result.toString();
    }

    @ShellMethod(key = {"getAllBooks", "gabm"})
    public String getAllMongoBooks() {
        var books = StreamSupport.stream(bookMongoRepository.findAll().spliterator(), false).toList();

        StringBuilder result = new StringBuilder();

        if (!books.isEmpty()) {
            books.forEach(book -> result.append(String.format("Id %s:\nName: %s\nAuthor: %s\nGenre: %s\nComments: %s\n",
                    book.getId(), book.getName(), book.getGenre().getName(), book.getAuthor().getName(), book.getComments())));
        } else {
            result.append("No books found");
        }

        return result.toString();
    }

}
