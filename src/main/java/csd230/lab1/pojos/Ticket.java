package csd230.lab1.pojos;

import java.time.LocalDate;
import java.util.Objects;

public class Ticket extends Product {
    private String eventName;
    private LocalDate eventDate;

    public Ticket() {}

    public Ticket(String name, String description, double price, String eventName, LocalDate eventDate) {
        super(name, description, price);
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", price=" + getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Ticket that = (Ticket) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
