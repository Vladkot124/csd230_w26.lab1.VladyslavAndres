package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final ProductRepository productRepository;

    public BookController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String list(Model model) {
        List<ProductEntity> all = productRepository.findAll();
        List<BookEntity> books = all.stream()
                .filter(p -> p instanceof BookEntity)
                .map(p -> (BookEntity) p)
                .toList();

        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        ProductEntity p = productRepository.findById(id).orElseThrow();
        if (!(p instanceof BookEntity book)) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "bookDetails";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "addBook";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute("book") BookEntity book) {
        productRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ProductEntity p = productRepository.findById(id).orElseThrow();
        if (!(p instanceof BookEntity book)) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id, @ModelAttribute("book") BookEntity formBook) {
        ProductEntity p = productRepository.findById(id).orElseThrow();
        if (!(p instanceof BookEntity book)) {
            return "redirect:/books";
        }

        book.setName(formBook.getName());
        book.setDescription(formBook.getDescription());
        book.setPrice(formBook.getPrice());
        book.setAuthor(formBook.getAuthor());
        book.setIsbn(formBook.getIsbn());
        book.setCopies(formBook.getCopies());

        productRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/books";
    }

}
