package csd230.lab1.repositories;

import com.github.javafaker.Faker;
import csd230.lab1.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void crud_book() {
        Faker faker = new Faker();

        BookEntity book = new BookEntity(
                faker.book().title(),
                faker.lorem().sentence(),
                49.99,
                faker.code().isbn13(),
                faker.book().author()
        );

        // CREATE
        book = bookRepository.save(book);
        assertNotNull(book.getId());

        // READ
        BookEntity found = bookRepository.findById(book.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(book.getIsbn(), found.getIsbn());

        // UPDATE
        found.setPrice(99.99);
        bookRepository.save(found);
        BookEntity updated = bookRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals(99.99, updated.getPrice());

        // DELETE
        bookRepository.delete(updated);
        assertTrue(bookRepository.findById(updated.getId()).isEmpty());
    }
}
