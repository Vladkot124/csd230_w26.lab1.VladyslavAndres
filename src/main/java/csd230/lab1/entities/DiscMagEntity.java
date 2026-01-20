package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("DISCMAG")
public class DiscMagEntity extends MagazineEntity {

    private String discType;

    public DiscMagEntity() {}

    public DiscMagEntity(String name, String description, double price, int issueNumber, String publisher, String discType) {
        super(name, description, price, issueNumber, publisher);
        this.discType = discType;
    }

    public String getDiscType() { return discType; }
    public void setDiscType(String discType) { this.discType = discType; }

    @Override
    public String toString() {
        return "DiscMagEntity{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", discType='" + discType + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        DiscMagEntity that = (DiscMagEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
