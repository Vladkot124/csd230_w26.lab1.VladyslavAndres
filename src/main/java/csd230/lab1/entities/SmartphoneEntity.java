package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("SMARTPHONE")
public class SmartphoneEntity extends ElectronicsEntity {

    private int storageGb;
    private boolean has5g;

    public SmartphoneEntity() {}

    public SmartphoneEntity(String name, String description, double price, String brand, int warrantyMonths, int storageGb, boolean has5g) {
        super(name, description, price, brand, warrantyMonths);
        this.storageGb = storageGb;
        this.has5g = has5g;
    }

    public int getStorageGb() { return storageGb; }
    public void setStorageGb(int storageGb) { this.storageGb = storageGb; }

    public boolean isHas5g() { return has5g; }
    public void setHas5g(boolean has5g) { this.has5g = has5g; }

    @Override
    public String toString() {
        return "SmartphoneEntity{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", storageGb=" + storageGb +
                ", has5g=" + has5g +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        SmartphoneEntity that = (SmartphoneEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
