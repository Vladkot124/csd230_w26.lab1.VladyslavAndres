package csd230.lab1;

import com.github.javafaker.Faker;
import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final MagazineRepository magazineRepository;
    private final TicketRepository ticketRepository;
    private final CartRepository cartRepository;
    private final DiscMagRepository discMagRepository;   // ✅ ADDED

    public Application(BookRepository bookRepository,
                       MagazineRepository magazineRepository,
                       TicketRepository ticketRepository,
                       CartRepository cartRepository,
                       DiscMagRepository discMagRepository) {  // ✅ ADDED
        this.bookRepository = bookRepository;
        this.magazineRepository = magazineRepository;
        this.ticketRepository = ticketRepository;
        this.cartRepository = cartRepository;
        this.discMagRepository = discMagRepository;       // ✅ ADDED
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {
        Faker faker = new Faker();

        // -------------------------
        // CREATE (C)
        // -------------------------
        String priceString = faker.commerce().price();
        double bookPrice = Double.parseDouble(priceString);

        BookEntity book = new BookEntity(
                faker.book().title(),
                bookPrice,
                10,
                faker.book().author(),
                "ISBN-" + faker.number().digits(6)
        );
        bookRepository.save(book);

        MagazineEntity magazine = new MagazineEntity(
                faker.lorem().word() + " Magazine",
                12.99,
                20,
                50,
                LocalDateTime.now()
        );
        magazineRepository.save(magazine);

        TicketEntity ticket = new TicketEntity(
                "Concert - " + faker.rockBand().name(),
                49.99
        );
        ticketRepository.save(ticket);

        // ✅ CREATE DiscMag
        DiscMagEntity discMag = new DiscMagEntity(
                faker.book().title() + " DiscMag",
                9.99,
                15,
                100,
                LocalDateTime.now(),
                true
        );
        discMagRepository.save(discMag);

        CartEntity cart = new CartEntity();
        cartRepository.save(cart);

        // Add products to cart (Many-to-Many)
        cart.addProduct(book);
        cart.addProduct(magazine);
        cart.addProduct(ticket);
        cart.addProduct(discMag);     // ✅ ADDED
        cartRepository.save(cart);

        // -------------------------
        // READ (R)
        // -------------------------
        System.out.println("\n=== READ: All Books ===");
        for (BookEntity b : bookRepository.findAll()) {
            System.out.println(b);
        }

        System.out.println("\n=== READ: Book queries required by lab ===");
        List<BookEntity> likeTitle = bookRepository.findByTitleLike("%" + book.getTitle().substring(0, 2) + "%");
        System.out.println("findByTitleLike result size: " + likeTitle.size());

        List<BookEntity> byIsbn = bookRepository.findByIsbn(book.getIsbn());
        System.out.println("findByIsbn result size: " + byIsbn.size());

        List<BookEntity> inRange = bookRepository.findBooksInPriceRange(1.0, 999.0);
        System.out.println("findBooksInPriceRange result size: " + inRange.size());

        System.out.println("\n=== READ: All DiscMags ===");
        for (DiscMagEntity d : discMagRepository.findAll()) {
            System.out.println(d);
        }

        System.out.println("\n=== READ: Print carts and their products (required) ===");
        for (CartEntity c : cartRepository.findAll()) {
            System.out.println(c);
            for (ProductEntity p : c.getProducts()) {
                System.out.println("   -> " + p);
            }
        }

        // -------------------------
        // UPDATE (U)
        // -------------------------
        System.out.println("\n=== UPDATE: Update book author and magazine orderQty ===");
        book.setAuthor(book.getAuthor() + " UPDATED");
        bookRepository.save(book);

        magazine.setOrderQty(magazine.getOrderQty() + 10);
        magazineRepository.save(magazine);

        System.out.println("\n=== UPDATE: DiscMag hasDisc -> false ===");
        discMag.setHasDisc(false);
        discMagRepository.save(discMag);

        // -------------------------
        // DELETE (D)
        // -------------------------
        System.out.println("\n=== DELETE: Delete the ticket ===");
        ticketRepository.deleteById(ticket.getId());

        System.out.println("\n=== DELETE: Delete the discMag ===");
        discMagRepository.deleteById(discMag.getId());

        System.out.println("\n=== DONE ===");
    }
}
