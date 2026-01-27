package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOOK")
public class BookEntity extends ProductEntity {

    private String isbn;
    private String author;

    // ✅ Option B: Integer allows NULL from DB
    private Integer copies;

    public BookEntity() {
    }

    // This constructor matches: BookEntity(String, String, double, String, String)
    public BookEntity(String name, String description, double price, String isbn, String author) {
        super(name, description, price);
        this.isbn = isbn;
        this.author = author;
        this.copies = 5; // default for new objects you create
    }

    public BookEntity(String name, String description, double price, String isbn, String author, Integer copies) {
        super(name, description, price);
        this.isbn = isbn;
        this.author = author;
        this.copies = copies;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", copies=" + copies +
                ", price=" + getPrice() +
                '}';
    }
}
