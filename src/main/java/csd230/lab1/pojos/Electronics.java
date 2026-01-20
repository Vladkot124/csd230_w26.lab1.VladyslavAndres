package csd230.lab1.pojos;

import java.util.Objects;

public abstract class Electronics extends Product {
    private String brand;
    private int warrantyMonths;

    public Electronics() {}

    public Electronics(String name, String description, double price, String brand, int warrantyMonths) {
        super(name, description, price);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    @Override
    public String toString() {
        return "Electronics{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", brand='" + brand + '\'' +
                ", warrantyMonths=" + warrantyMonths +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Electronics that = (Electronics) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
