package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String bookListPage(Model model) {
        List<BookDto> books = libraryService.getAllBooks();
        List<GenreDto> genres = libraryService.getAllGenres();
        List<AuthorDto> authors = libraryService.getAllAuthors();

        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "list";
    }

    @GetMapping("/book/info")
    public String bookPage(@RequestParam("id") long id, Model model) {
        BookDto book = libraryService.getBookById(id);
        List<CommentDto> comments = libraryService.getCommentsByBookId(id);
        List<GenreDto> genres = libraryService.getAllGenres();
        List<AuthorDto> authors = libraryService.getAllAuthors();


        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

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

        libraryService.saveBook(new Book(id, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));

        return "redirect:/book/info?id=" + id;
    }

    @PostMapping("/book/new")
    public String saveBook(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "author_id") Long authorId,
            @RequestParam(value = "genre_id") Long genreId
    ) {

        libraryService.saveBook(new Book(null, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));

        return "redirect:/book";
    }

    @PostMapping("/book/comment")
    public String saveComment(
            @RequestParam(value = "book_id") long id,
            @RequestParam(value = "comment_text") String text
    ) {
        libraryService.saveComment(new Comment(null, Book.builder().id(id).build(), text));

        return "redirect:/book/info?id=" + id;
    }
}
