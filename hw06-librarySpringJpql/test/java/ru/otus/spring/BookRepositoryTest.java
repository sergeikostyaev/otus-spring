package ru.otus.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.impl.BookRepositoryImpl;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@DataJpaTest
@Import(BookRepositoryImpl.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepositoryImpl bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenBookRepositoryGetById_always_correctProcessing() {
        Book book = bookRepository.getById(1L);

        Assertions.assertThat(book.getName()).isEqualTo("Жизнь двенадцати цезарей");
    }

    @Test
    public void whenBookRepositoryUpdateBookById_always_correctProcessing() {
        bookRepository.save(new Book(1L, "updated", new Author(1L,null), new Genre(1L, null), null));

        Assertions.assertThat(bookRepository.getById(1L).getName()).isEqualTo("updated");
    }

    @Test
    public void whenBookRepositoryGetAll_always_correctProcessing() {
        bookRepository.getAll();

        Assertions.assertThat(bookRepository.getAll().size()).isEqualTo(4);
    }

    @Test
    public void whenBookRepositoryDelete_always_correctProcessing() {
        bookRepository.save(new Book(null, "updated", new Author(1L,null), new Genre(1L, null), null));

        var book = bookRepository.getByName("updated").get(0);

        Assertions.assertThat(bookRepository.getById(book.getId()).getName()).isEqualTo("updated");

        bookRepository.remove(book.getId());

        Assertions.assertThat(bookRepository.getById(book.getId())).isNull();
    }

}
