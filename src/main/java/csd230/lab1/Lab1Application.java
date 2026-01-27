package csd230.lab1;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Lab1Application implements CommandLineRunner {

    private final BookEntityRepository bookEntityRepository;
    private final MagazineRepository magazineRepository;
    private final DiscMagRepository discMagRepository;
    private final TicketRepository ticketRepository;

    private final SmartphoneRepository smartphoneRepository;
    private final LaptopRepository laptopRepository;

    private final CartRepository cartRepository;

    public Lab1Application(
            BookEntityRepository bookEntityRepository,
            MagazineRepository magazineRepository,
            DiscMagRepository discMagRepository,
            TicketRepository ticketRepository,
            SmartphoneRepository smartphoneRepository,
            LaptopRepository laptopRepository,
            CartRepository cartRepository
    ) {
        this.bookEntityRepository = bookEntityRepository;
        this.magazineRepository = magazineRepository;
        this.discMagRepository = discMagRepository;
        this.ticketRepository = ticketRepository;
        this.smartphoneRepository = smartphoneRepository;
        this.laptopRepository = laptopRepository;
        this.cartRepository = cartRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

        Faker faker = new Faker();
        Commerce cm = faker.commerce();

        // =========================================
        // STEP 1: CREATE (save entities)
        // =========================================
        System.out.println("=== STEP 1: CREATE (save entities) ===");

        BookEntity b1 = new BookEntity(
                faker.book().title(),
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                faker.book().author(),
                faker.code().isbn13()
        );

        BookEntity b2 = new BookEntity(
                faker.book().title(),
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                faker.book().author(),
                faker.code().isbn13()
        );

        MagazineEntity m1 = new MagazineEntity(
                "Tech Monthly",
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                100,
                "TechPress"
        );

        DiscMagEntity d1 = new DiscMagEntity(
                "Music Disc Mag",
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                45,
                "DiscPublisher",
                "CD"
        );

        TicketEntity t1 = new TicketEntity(
                "Event Ticket",
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                "Concert Slayer",
                LocalDate.now().plusDays(57)
        );

        SmartphoneEntity s1 = new SmartphoneEntity(
                cm.productName(),
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                faker.company().name(),
                faker.number().numberBetween(6, 36),
                faker.number().numberBetween(64, 512),
                faker.bool().bool()
        );

        LaptopEntity l1 = new LaptopEntity(
                cm.productName(),
                faker.lorem().sentence(6),
                Double.parseDouble(cm.price()),
                faker.company().name(),
                faker.number().numberBetween(6, 36),
                faker.number().numberBetween(8, 64),
                "Intel i7"
        );

        b1 = bookEntityRepository.save(b1);
        b2 = bookEntityRepository.save(b2);
        m1 = magazineRepository.save(m1);
        d1 = discMagRepository.save(d1);
        t1 = ticketRepository.save(t1);
        s1 = smartphoneRepository.save(s1);
        l1 = laptopRepository.save(l1);

        System.out.println("Saved: " + b1);
        System.out.println("Saved: " + b2);
        System.out.println("Saved: " + m1);
        System.out.println("Saved: " + d1);
        System.out.println("Saved: " + t1);
        System.out.println("Saved (niche): " + s1);
        System.out.println("Saved (niche): " + l1);

        // =========================================
        // STEP 2: MANY-TO-MANY (Cart contains Products)
        // =========================================
        System.out.println("\n=== STEP 2: MANY-TO-MANY (Cart contains Products) ===");

        CartEntity cart = new CartEntity();
        cart.setCustomerName("Vlad Customer");
        cart.setCreatedAt(LocalDateTime.now());

        cart.addProduct(b1);
        cart.addProduct(m1);
        cart.addProduct(d1);
        cart.addProduct(t1);
        cart.addProduct(s1);
        cart.addProduct(l1);

        cart = cartRepository.save(cart);

        System.out.println("Saved Cart: " + cart);
        printCartProducts(cart);

        // =========================================
        // STEP 3: READ (findAll + queries)
        // =========================================
        System.out.println("\n=== STEP 3: READ (findAll + queries) ===");

        List<BookEntity> allBooks = bookEntityRepository.findAll();
        System.out.println("All books: " + allBooks);

//        List<BookEntity> byIsbn = bookEntityRepository.findByIsbn(b1.getIsbn());
//        System.out.println("Books by ISBN (b1): " + byIsbn);
//
//        List<BookEntity> nameLike = bookEntityRepository.findByNameLike("%a%");
//        System.out.println("Books name LIKE '%a%': " + nameLike);
//
//        List<BookEntity> priceRange = bookEntityRepository.findBooksInPriceRange(10.0, 150.0);
//        System.out.println("Books price range 10-150: " + priceRange);

        // =========================================
        // STEP 4: UPDATE
        // =========================================
        System.out.println("\n=== STEP 4: UPDATE ===");

        BookEntity toUpdate = bookEntityRepository.findById(b1.getId()).orElseThrow();
        toUpdate.setPrice(toUpdate.getPrice() + 10.0);
        BookEntity updated = bookEntityRepository.save(toUpdate);

        System.out.printf("Updated book: id=%d, name='%s', price=%.2f%n",
                updated.getId(), updated.getName(), updated.getPrice());

        // =========================================
        // STEP 5: DELETE (fix FK constraint)
        // =========================================
        System.out.println("\n=== STEP 5: DELETE (FIX FK ERROR) ===");

        CartEntity freshCart = cartRepository.findById(cart.getId()).orElseThrow();
        TicketEntity freshTicket = ticketRepository.findById(t1.getId()).orElseThrow();

        // unlink first (prevents FK crash)
        freshCart.getProducts().remove(freshTicket);
        cartRepository.save(freshCart);

        // now delete safely
        ticketRepository.delete(freshTicket);

        System.out.println("Deleted ticket id=" + t1.getId() + " after unlinking from cart.");

        // show cart again after delete (proof many-to-many updated)
        CartEntity afterDeleteCart = cartRepository.findById(cart.getId()).orElseThrow();
        System.out.println("\nCart after deleting ticket:");
        printCartProducts(afterDeleteCart);

        System.out.println("\n=== LAB COMPLETE: CREATE + M2M + READ + UPDATE + DELETE ===");
    }

    private void printCartProducts(CartEntity cart) {
        System.out.println("Cart Products:");
        for (ProductEntity p : cart.getProducts()) {
            System.out.println(" - " + p);
        }
    }
}
