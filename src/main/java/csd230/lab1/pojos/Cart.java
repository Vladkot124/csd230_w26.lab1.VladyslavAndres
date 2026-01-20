package csd230.lab1.pojos;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Cart {
    private Long id;
    private String customerName;
    private Set<Product> products = new LinkedHashSet<>();

    public Cart() {}

    public Cart(String customerName) {
        this.customerName = customerName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Set<Product> getProducts() { return products; }

    public void addProduct(Product p) {
        if (p != null) products.add(p);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", productsCount=" + products.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
