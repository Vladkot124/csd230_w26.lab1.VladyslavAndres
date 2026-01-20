package csd230.lab1.pojos;

import java.util.Objects;

// Base class for things like Book, Magazine, DiscMag
public abstract class Publication extends Product {

    public Publication() {
        super();
    }

    public Publication(String name, String description, double price) {
        super(name, description, price);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Publication that = (Publication) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
