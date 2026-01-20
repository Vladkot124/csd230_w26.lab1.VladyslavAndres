package csd230.lab1.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    // inverse side of many-to-many (CartEntity owns join table)
    @ManyToMany(mappedBy = "products")
    private Set<CartEntity> carts = new HashSet<>();

    protected ProductEntity() {
    }

    protected ProductEntity(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    // ✅ FIX for your UPDATE section
    public void setPrice(double price) {
        this.price = price;
    }

    // ✅ FIX for CartEntity add/remove both sides
    public Set<CartEntity> getCarts() {
        return carts;
    }

    // Optional but safe (keeps JPA from you accidentally replacing the set)
    protected void setCarts(Set<CartEntity> carts) {
        this.carts = carts;
    }
}
