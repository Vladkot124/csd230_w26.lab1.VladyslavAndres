package csd230.lab1.repositories;

import csd230.lab1.entities.DiscMagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscMagRepository extends JpaRepository<DiscMagEntity, Long> {
    // “similar relevant query” example
    List<DiscMagEntity> findByTitleContainingIgnoreCase(String title);
}
