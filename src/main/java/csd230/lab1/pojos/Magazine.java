package csd230.lab1.pojos;

import java.util.Objects;

public class Magazine extends Product {
    private int issueNumber;
    private String publisher;

    public Magazine() {}

    public Magazine(String name, String description, double price, int issueNumber, String publisher) {
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
        return "Magazine{" +
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
        Magazine that = (Magazine) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
