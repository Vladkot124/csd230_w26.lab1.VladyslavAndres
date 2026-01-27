package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final OrderEntityRepository orderRepo;
    private final BookEntityRepository bookRepo;

    public CartController(CartRepository cartRepo,
                          ProductRepository productRepo,
                          OrderEntityRepository orderRepo,
                          BookEntityRepository bookRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.bookRepo = bookRepo;
    }

    // helper: always use cart id 1
    private CartEntity getDefaultCart() {
        return cartRepo.findById(1L).orElseGet(() -> {
            CartEntity c = new CartEntity("Default Customer");
            // ensure id=1 exists only if DB empty; otherwise it will auto-id
            return cartRepo.save(c);
        });
    }

    // VIEW CART
    @GetMapping
    public String viewCart(Model model) {
        CartEntity cart = getDefaultCart();
        model.addAttribute("cart", cart);

        double total = 0.0;
        for (ProductEntity p : cart.getProducts()) {
            total += p.getPrice();
        }
        model.addAttribute("total", total);

        return "cartDetails";
    }

    // ADD PRODUCT TO CART
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        CartEntity cart = getDefaultCart();
        ProductEntity product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            cart.addProduct(product);
            cartRepo.save(cart);
        }
        return "redirect:/cart";
    }

    // REMOVE PRODUCT FROM CART
    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        CartEntity cart = getDefaultCart();
        ProductEntity product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            cart.removeProduct(product);
            cartRepo.save(cart);
        }
        return "redirect:/cart";
    }


    @PostMapping("/checkout")
    public String checkout() {
        CartEntity cart = getDefaultCart();

        if (cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        // process each product
        for (ProductEntity p : cart.getProducts()) {
            total += p.getPrice();

            // decrement copies only for BookEntity
            if (p instanceof BookEntity book) {
                Integer current = book.getCopies();
                if (current == null) current = 5; // treat old DB NULL as 5

                if (current > 0) {
                    book.setCopies(current - 1);
                    bookRepo.save(book);
                }

            }

            order.addProduct(p);
        }

        order.setTotalAmount(total);

        // save order first
        OrderEntity savedOrder = orderRepo.save(order);

        // clear cart (use removeProduct to keep both sides consistent)
        // IMPORTANT: avoid ConcurrentModification by copying set
        for (ProductEntity p : new java.util.HashSet<>(cart.getProducts())) {
            cart.removeProduct(p);
        }
        cartRepo.save(cart);

        return "redirect:/orders/" + savedOrder.getId();
    }
}
