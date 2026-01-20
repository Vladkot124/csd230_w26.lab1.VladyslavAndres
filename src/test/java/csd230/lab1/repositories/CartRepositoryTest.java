package csd230.lab1.repositories;

import com.github.javafaker.Faker;
import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.MagazineEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MagazineRepository magazineRepository;

    @Test
    void crud_cart_many_to_many() {
        Faker faker = new Faker();

        BookEntity b = bookRepository.save(new BookEntity(
                faker.book().title(),
                faker.lorem().sentence(),
                20.00,
                faker.code().isbn13(),
                faker.book().author()
        ));

        MagazineEntity m = magazineRepository.save(new MagazineEntity(
                "Test Mag",
                faker.lorem().sentence(),
                10.00,
                5,
                "TestPub"
        ));

        CartEntity cart = new CartEntity("Test Customer");
        cart.addProduct(b);
        cart.addProduct(m);

        // CREATE
        cart = cartRepository.save(cart);
        assertNotNull(cart.getId());
        assertEquals(2, cart.getProducts().size());

        // READ
        CartEntity found = cartRepository.findById(cart.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(2, found.getProducts().size());

        // UPDATE
        found.setCustomerName("Updated Customer");
        cartRepository.save(found);
        CartEntity updated = cartRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals("Updated Customer", updated.getCustomerName());

        // DELETE
        cartRepository.delete(updated);
        assertTrue(cartRepository.findById(updated.getId()).isEmpty());
    }
}
