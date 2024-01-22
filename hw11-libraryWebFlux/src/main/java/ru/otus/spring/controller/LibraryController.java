package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    @GetMapping("/")
    public String df() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public Mono<Rendering> bookListPage() {
        return Mono.just(Rendering.view("list").build());
    }

    @GetMapping("/books/{id}")
    public Mono<Rendering> bookPage(@PathVariable("id") long id) {
        return Mono.just(Rendering.view("info").build());
    }
}
