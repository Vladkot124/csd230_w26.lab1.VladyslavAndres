package csd230.lab1.pojos;

import java.util.Objects;

public class Laptop extends Electronics {
    private int ramGb;
    private String cpuModel;

    public Laptop() {}

    public Laptop(String name, String description, double price, String brand, int warrantyMonths, int ramGb, String cpuModel) {
        super(name, description, price, brand, warrantyMonths);
        this.ramGb = ramGb;
        this.cpuModel = cpuModel;
    }

    public int getRamGb() { return ramGb; }
    public void setRamGb(int ramGb) { this.ramGb = ramGb; }

    public String getCpuModel() { return cpuModel; }
    public void setCpuModel(String cpuModel) { this.cpuModel = cpuModel; }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", ramGb=" + ramGb +
                ", cpuModel='" + cpuModel + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Laptop that = (Laptop) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
