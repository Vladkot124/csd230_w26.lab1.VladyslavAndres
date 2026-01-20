package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("MAGAZINE")
public class MagazineEntity extends ProductEntity {

    private int issueNumber;
    private String publisher;

    public MagazineEntity() {}

    public MagazineEntity(String name, String description, double price, int issueNumber, String publisher) {
        super(name, description, price);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public int getIssueNumber() { return issueNumber; }
    public void setIssueNumber(int issueNumber) { this.issueNumber = issueNumber; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    @Override
    public String toString() {
        return "MagazineEntity{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", issueNumber=" + issueNumber +
                ", publisher='" + publisher + '\'' +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        MagazineEntity that = (MagazineEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
