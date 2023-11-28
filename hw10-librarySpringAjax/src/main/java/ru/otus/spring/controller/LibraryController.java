package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

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
    public String bookPage(@PathVariable("id") long id, Model model) {
        BookDto book = libraryService.getBookById(id);
        List<CommentDto> comments = libraryService.getCommentsByBookId(id);


        model.addAttribute("book", book);
        model.addAttribute("comments", comments);

        return "info";
    }

    @PostMapping("/books/save")
    public String saveBook(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "author_id") Long authorId,
            @RequestParam(value = "genre_id") Long genreId
    ) {

        libraryService.saveBook(new Book(id, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));

        return "redirect:/book/info?id=" + id;
    }

    @PostMapping("/books/comment")
    public String saveComment(
            @RequestParam(value = "book_id") long id,
            @RequestParam(value = "comment_text") String text
    ) {
        libraryService.saveComment(new Comment(null, Book.builder().id(id).build(), text));

        return "redirect:/book/info?id=" + id;
    }
}
