package csd230.lab1.repositories;

import csd230.lab1.entities.TicketEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void crud_ticket() {
        TicketEntity t = new TicketEntity("Ticket", "Desc", 55.00, "Event", LocalDate.now().plusDays(10));

        t = ticketRepository.save(t);
        assertNotNull(t.getId());

        TicketEntity found = ticketRepository.findById(t.getId()).orElse(null);
        assertNotNull(found);

        found.setEventName("Updated Event");
        ticketRepository.save(found);

        TicketEntity updated = ticketRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals("Updated Event", updated.getEventName());

        ticketRepository.delete(updated);
        assertTrue(ticketRepository.findById(updated.getId()).isEmpty());
    }
}
