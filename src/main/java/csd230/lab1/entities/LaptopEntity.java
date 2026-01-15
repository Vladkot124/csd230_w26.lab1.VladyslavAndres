package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LAPTOP")
public class LaptopEntity extends ElectronicDeviceEntity {

    private int ramGb;

    public LaptopEntity() {}

    public LaptopEntity(String brand, int ramGb) {
        super(brand);
        this.ramGb = ramGb;
    }

    public int getRamGb() { return ramGb; }
    public void setRamGb(int ramGb) { this.ramGb = ramGb; }

    @Override
    public String toString() {
        return "Laptop{id=" + getId() + ", brand='" + getBrand() + "', ramGb=" + ramGb + "}";
    }
}
