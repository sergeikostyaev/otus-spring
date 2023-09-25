package ru.otus.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.impl.LibraryRepositoryImpl;
import ru.otus.spring.domain.Book;

import java.io.IOException;

@JdbcTest
@Import(LibraryRepositoryImpl.class)
public class LibraryRepositoryTest {
    @Autowired
    private LibraryRepositoryImpl libraryRepository;

    @Test
    public void whenLibraryRepositoryFindBookById_always_correctProcessing() throws IOException {
        Book book = libraryRepository.findBookById(1L);

        Assertions.assertThat(book.getName()).isEqualTo("Жизнь двенадцати цезарей");
    }

    @Test
    public void whenLibraryRepositoryUpdateBookById_always_correctProcessing() throws IOException {
        libraryRepository.updateBookById(1L, "updated", 1L, 1L);

        Assertions.assertThat(libraryRepository.findBookById(1L).getName()).isEqualTo("updated");
    }

}
