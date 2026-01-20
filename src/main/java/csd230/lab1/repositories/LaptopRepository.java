package csd230.lab1.repositories;

import csd230.lab1.entities.LaptopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaptopRepository extends JpaRepository<LaptopEntity, Long> {
    List<LaptopEntity> findByBrand(String brand);
    List<LaptopEntity> findByNameLike(String pattern);
}
