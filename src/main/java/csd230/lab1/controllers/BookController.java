package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartRepository;
import csd230.lab1.repositories.OrderEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookEntityRepository bookRepo;
    private final CartRepository cartRepo;
    private final OrderEntityRepository orderRepo;

    public BookController(BookEntityRepository bookRepo,
                          CartRepository cartRepo,
                          OrderEntityRepository orderRepo) {
        this.bookRepo = bookRepo;
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
    }


    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "bookList";
    }


    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        BookEntity book = bookRepo.findById(id).orElse(null);
        if (book == null) return "redirect:/books";
        model.addAttribute("book", book);
        return "bookDetails";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "addBook";
    }


    @PostMapping("/add")
    public String addSubmit(@ModelAttribute BookEntity book) {
        if (book.getCopies() == null) book.setCopies(5); // Option B safe default
        bookRepo.save(book);
        return "redirect:/books";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        BookEntity book = bookRepo.findById(id).orElse(null);
        if (book == null) return "redirect:/books";
        model.addAttribute("book", book);
        return "editBook";
    }


    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id, @ModelAttribute BookEntity formBook) {
        BookEntity book = bookRepo.findById(id).orElse(null);
        if (book == null) return "redirect:/books";

        book.setName(formBook.getName());
        book.setAuthor(formBook.getAuthor());
        book.setIsbn(formBook.getIsbn());
        book.setPrice(formBook.getPrice());
        book.setDescription(formBook.getDescription());
        book.setCopies(formBook.getCopies());

        bookRepo.save(book);
        return "redirect:/books";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        // 1) remove join table references first (prevents FK crash)
        cartRepo.deleteLinksForProduct(id);
        orderRepo.deleteLinksForProduct(id);

        // 2) then delete the product row
        bookRepo.deleteById(id);

        return "redirect:/books";
    }
}
