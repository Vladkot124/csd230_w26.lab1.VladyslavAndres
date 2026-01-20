package csd230.lab1.pojos;

import java.util.Objects;

public class DiscMag extends Magazine {
    private String discType; // CD/DVD/Bluray

    public DiscMag() {}

    public DiscMag(String name, String description, double price, int issueNumber, String publisher, String discType) {
        super(name, description, price, issueNumber, publisher);
        this.discType = discType;
    }

    public String getDiscType() { return discType; }
    public void setDiscType(String discType) { this.discType = discType; }

    @Override
    public String toString() {
        return "DiscMag{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", discType='" + discType + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        DiscMag that = (DiscMag) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
