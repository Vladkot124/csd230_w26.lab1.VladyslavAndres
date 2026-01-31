package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import csd230.lab1.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class CartController {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final OrderEntityRepository orderRepo;
    private final BookEntityRepository bookRepo;

    public CartController(
            CartRepository cartRepo,
            ProductRepository productRepo,
            OrderEntityRepository orderRepo,
            BookEntityRepository bookRepo
    ) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.bookRepo = bookRepo;
    }

    // Always use cart id = 1 for the lab (simple demo)
    private CartEntity getCart() {
        return cartRepo.findById(1L).orElseGet(() -> {
            CartEntity c = new CartEntity();
            c.setId(1L);
            c.setCustomerName("Vlad Customer");
            c.setCreatedAt(LocalDateTime.now());
            return cartRepo.save(c);
        });
    }

    @GetMapping("/cart")
    public String cartPage(Model model) {
        CartEntity cart = getCart();

        double total = cart.getProducts().stream()
                .mapToDouble(ProductEntity::getPrice)
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cartDetails";
    }

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        CartEntity cart = getCart();
        ProductEntity product = productRepo.findById(productId).orElseThrow();

        // If you have addProduct() syncing both sides, use it:
        cart.addProduct(product);

        cartRepo.save(cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        CartEntity cart = getCart();
        ProductEntity product = productRepo.findById(productId).orElseThrow();

        cart.removeProduct(product);

        cartRepo.save(cart);
        return "redirect:/cart";
    }


    @Transactional
    @PostMapping("/cart/checkout")
    public String checkout() {
        CartEntity cart = getCart();

        if (cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;


        for (ProductEntity p : cart.getProducts()) {
            total += p.getPrice();
            order.getProducts().add(p);


            if (p instanceof BookEntity) {
                BookEntity managedBook = bookRepo.findById(p.getId()).orElseThrow();
                Integer copies = managedBook.getCopies();
                int safeCopies = (copies == null) ? 0 : copies;

                if (safeCopies > 0) {
                    managedBook.setCopies(safeCopies - 1);
                } else {
                    managedBook.setCopies(0);
                }

                bookRepo.save(managedBook);
            }
        }

        order.setTotalAmount(total);

        // 2) Save order
        orderRepo.save(order);

        // 3) Clear cart
        cart.getProducts().clear();
        cartRepo.save(cart);

        return "redirect:/orders/" + order.getId();
    }
}
