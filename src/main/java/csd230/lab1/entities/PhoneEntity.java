package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PHONE")
public class PhoneEntity extends ElectronicDeviceEntity {

    private boolean has5g;

    public PhoneEntity() {}

    public PhoneEntity(String brand, boolean has5g) {
        super(brand);
        this.has5g = has5g;
    }

    public boolean isHas5g() { return has5g; }
    public void setHas5g(boolean has5g) { this.has5g = has5g; }

    @Override
    public String toString() {
        return "Phone{id=" + getId() + ", brand='" + getBrand() + "', has5g=" + has5g + "}";
    }
}
