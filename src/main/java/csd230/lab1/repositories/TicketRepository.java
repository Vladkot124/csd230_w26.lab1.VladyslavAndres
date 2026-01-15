package csd230.lab1.repositories;

import csd230.lab1.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    // “similar relevant query” example
    List<TicketEntity> findByDescriptionContainingIgnoreCase(String text);
}
