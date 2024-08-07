package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.service.LibraryService;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/")
    public String df() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String bookListPage() {
        return "list";
    }

    @GetMapping("/books/{id}")
    public String bookPage(@PathVariable("id") long id) {
        return "info";
    }
}
