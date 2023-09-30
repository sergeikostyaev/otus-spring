package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.dao.LibraryRepository;
import ru.otus.spring.service.IoService;
import ru.otus.spring.service.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final LibraryService libraryService;
    private final IoService ioService;

    @ShellMethod(key = {"fb", "find book"})
    public void findBookById(long id){
        ioService.print(String.valueOf(libraryService.findBookById(id)));
    }

    @ShellMethod(key = {"ab", "add book"})
    public void addBook(String name, long authorId, long genreId){
        libraryService.insertBook(name, authorId, genreId);
        ioService.print("Book " + name + " added");
    }

    @ShellMethod(key = {"db", "delete book"})
    public void deleteBook(long id){
        libraryService.removeBookById(id);
        ioService.print("Book with id " + id + " deleted");
    }

    @ShellMethod(key = {"ub", "update book"})
    public void updateBook(long id, String bookName, Long authorId, Long genreId){
        libraryService.updateBookById(id, bookName, authorId, genreId);
        ioService.print("Book with id " + id + " updated");
    }

}
