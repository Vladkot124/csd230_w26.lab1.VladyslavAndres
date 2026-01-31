package csd230.lab1.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new HashSet<>();

    public CartEntity() {
    }

    public CartEntity(String customerName) {
        this.customerName = customerName;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }


    public void addProduct(ProductEntity product) {
        this.products.add(product);
        product.getCarts().add(this);
    }

    public void removeProduct(ProductEntity product) {
        this.products.remove(product);
        product.getCarts().remove(this);
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", productsCount=" + (products == null ? 0 : products.size()) +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
