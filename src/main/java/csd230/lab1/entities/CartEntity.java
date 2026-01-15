package csd230.lab1.entities;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;



@Entity
@Table(name = "cart_entity")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new LinkedHashSet<>();

    public void addProduct(ProductEntity product) {
        this.products.add(product);
        product.getCarts().add(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Set<ProductEntity> getProducts() { return products; }
    public void setProducts(Set<ProductEntity> products) { this.products = products; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartEntity)) return false;
        CartEntity other = (CartEntity) o;
        return id != null && id.equals(other.id);
    }
    @Override
    public String toString() {
        return "CartEntity{id=" + id + ", productsCount=" + products.size() + "}";
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}