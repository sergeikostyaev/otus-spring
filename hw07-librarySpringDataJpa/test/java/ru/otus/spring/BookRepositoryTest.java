package ru.otus.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.mapper.impl.BookMapper;
import ru.otus.spring.mapper.impl.CommentMapper;
import ru.otus.spring.service.impl.LibraryServiceImpl;

@DataJpaTest
@Import({LibraryServiceImpl.class, BookMapper.class, CommentMapper.class})
public class BookRepositoryTest {
    @Autowired
    private LibraryServiceImpl libraryService;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenLibraryServiceGetBookById_always_correctProcessing() {
        BookDto book = libraryService.getBookById(1L);

        Assertions.assertThat(book.getName()).isEqualTo("Жизнь двенадцати цезарей");
    }

    @Test
    public void whenLibraryServiceUpdateBookById_always_correctProcessing() {
        libraryService.saveBook(new Book(1L, "updated", new Author(1L,null), new Genre(1L, null), null));

        Assertions.assertThat(libraryService.getBookById(1L).getName()).isEqualTo("updated");
    }

    @Test
    public void whenLibraryServiceGetAllBooks_always_correctProcessing() {
        Assertions.assertThat(libraryService.getAllBooks().size()).isEqualTo(4);
    }

//    @Test
//    public void whenLibraryServiceGetByNameAndDelete_always_correctProcessing() {
//        libraryService.saveBook(new Book(null, "updated", new Author(1L,null), new Genre(1L, null), null));
//
//        var book = libraryService.getBooksByName("updated").get(0);
//
//        Assertions.assertThat(libraryService.getBookById(book.getId()).getName()).isEqualTo("updated");
//
//        libraryService.removeBookById(book.getId());
//
//        Assertions.assertThat(libraryService.getBookById(book.getId())).isNull();
//    }

}
