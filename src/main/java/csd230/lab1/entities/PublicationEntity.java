package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

// Base JPA entity for BookEntity, MagazineEntity, DiscMagEntity
@Entity
@DiscriminatorValue("PUBLICATION")
public abstract class PublicationEntity extends ProductEntity {

    public PublicationEntity() {
        super();
    }

    public PublicationEntity(String name, String description, double price) {
        super(name, description, price);
    }

    @Override
    public String toString() {
        return "PublicationEntity{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        PublicationEntity that = (PublicationEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
