package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookEntityRepository bookRepo;

    public BookController(BookEntityRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "bookList";
    }

    // DETAILS
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        BookEntity book = bookRepo.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "bookDetails";
    }

    // ADD (GET form)
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "addBook";
    }

    // ADD (POST)
    @PostMapping("/add")
    public String addSubmit(@ModelAttribute BookEntity book) {
        if (book.getCopies() == null) book.setCopies(0);
        bookRepo.save(book);
        return "redirect:/books";
    }

    // EDIT (GET form)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        BookEntity book = bookRepo.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "editBook";
    }

    // EDIT (POST)
    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id, @ModelAttribute BookEntity formBook) {
        BookEntity book = bookRepo.findById(id).orElseThrow();
        book.setName(formBook.getName());
        book.setAuthor(formBook.getAuthor());
        book.setIsbn(formBook.getIsbn());
        book.setPrice(formBook.getPrice());
        book.setCopies(formBook.getCopies() == null ? 0 : formBook.getCopies());
        book.setDescription(formBook.getDescription());
        bookRepo.save(book);
        return "redirect:/books";
    }

    // DELETE (POST)
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookRepo.deleteById(id); // ✅ NOT static
        return "redirect:/books";
    }
}
