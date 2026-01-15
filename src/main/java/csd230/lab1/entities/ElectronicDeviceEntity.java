package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELECTRONIC")
public abstract class ElectronicDeviceEntity extends ProductEntity {

    private String brand;

    public ElectronicDeviceEntity() {}

    public ElectronicDeviceEntity(String brand) {
        this.brand = brand;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + getId() + ", brand='" + brand + "'}";
    }
}
