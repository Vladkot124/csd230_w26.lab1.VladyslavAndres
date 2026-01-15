package csd230.lab1.entities;

import csd230.lab1.pojos.SaleableItem;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ProductEntity implements Serializable, SaleableItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "products")
    private Set<CartEntity> carts = new HashSet<>();
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Set<CartEntity> getCarts() { return carts; }
    public void setCarts(Set<CartEntity> carts) { this.carts = carts; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + "} : " + super.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        ProductEntity other = (ProductEntity) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
