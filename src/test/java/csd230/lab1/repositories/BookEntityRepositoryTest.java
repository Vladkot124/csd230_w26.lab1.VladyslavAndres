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
public class BookEntityRepositoryTest {

    @Autowired
    private BookEntityRepository bookEntityRepository;

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
        book = bookEntityRepository.save(book);
        assertNotNull(book.getId());

        // READ
        BookEntity found = bookEntityRepository.findById(book.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(book.getIsbn(), found.getIsbn());

        // UPDATE
        found.setPrice(99.99);
        bookEntityRepository.save(found);
        BookEntity updated = bookEntityRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals(99.99, updated.getPrice());

        // DELETE
        bookEntityRepository.delete(updated);
        assertTrue(bookEntityRepository.findById(updated.getId()).isEmpty());
    }
}
