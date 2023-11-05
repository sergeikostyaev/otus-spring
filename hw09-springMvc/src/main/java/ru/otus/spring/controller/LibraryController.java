package ru.otus.spring.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/")
    public String df() {
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String bookListPage(Model model) {
        List<BookDto> books = libraryService.getAllBooks();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/info")
    public String bookPage(@RequestParam("id") long id, Model model) {
        BookDto book = libraryService.getBookById(id);
        model.addAttribute("book", book);
        return "info";
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam long id) {
        libraryService.removeBookById(id);
        return "redirect:/book";
    }

    @PostMapping("/book/save")
    public String saveBook(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "author_id") Long authorId,
            @RequestParam(value = "genre_id") Long genreId
    ) {

        var book = libraryService.saveBook(new Book(id, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));
        System.out.println(book);
        return "redirect:/info?id=" + id;
    }

    @PostMapping("/book/new")
    public String saveBook(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "author_id") Long authorId,
            @RequestParam(value = "genre_id") Long genreId
    ) {

        var book = libraryService.saveBook(new Book(null, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));

        return "redirect:/book";
    }

    @PostMapping("/book/comment")
    public String saveComment(
            @RequestParam(value = "book_id") long id,
            @RequestParam(value = "comment_text") String text
    ) {
        libraryService.saveComment(new Comment(null, Book.builder().id(1L).build(), text));

        return "redirect:/info?id=" + id;
    }
}
