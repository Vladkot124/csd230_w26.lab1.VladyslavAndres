package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.CartRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import csd230.lab1.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderEntityRepository orderEntityRepository;

    public CartController(
            CartRepository cartRepository,
            ProductRepository productRepository,
            OrderEntityRepository orderEntityRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderEntityRepository = orderEntityRepository;
    }

    @GetMapping("/cart")
    public String cartDetails(Model model) {
        CartEntity cart = cartRepository.findById(1L).orElseGet(() -> {
            CartEntity c = new CartEntity("Vlad Customer");
            // IMPORTANT: if your setId(long) exists and you really want ID=1 fixed:
            // c.setId(1L);  // only do this if you MUST force ID=1
            return cartRepository.save(c);
        });

        double total = cart.getProducts()
                .stream()
                .mapToDouble(ProductEntity::getPrice)
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "cartDetails";
    }

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        CartEntity cart = cartRepository.findById(1L).orElseThrow();
        ProductEntity product = productRepository.findById(productId).orElseThrow();
        cart.getProducts().add(product);
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        CartEntity cart = cartRepository.findById(1L).orElseThrow();
        cart.getProducts().removeIf(p -> p.getId().equals(productId));
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout() {
        CartEntity cart = cartRepository.findById(1L).orElseThrow();
        if (cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;
        List<ProductEntity> purchased = new ArrayList<>();

        for (ProductEntity p : cart.getProducts()) {
            total += p.getPrice();
            purchased.add(p);

            if (p instanceof BookEntity book) {
                int copies = book.getCopies();
                if (copies > 0) {
                    book.setCopies(copies - 1);
                    productRepository.save(book);
                }
            }
        }

        order.setTotalAmount(total);
        order.getProducts().addAll(purchased);

        cart.getProducts().clear();

        orderEntityRepository.save(order);
        cartRepository.save(cart);

        return "redirect:/orders/" + order.getId();
    }
}
