package csd230.lab1.repositories;

import csd230.lab1.entities.SmartphoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmartphoneRepository extends JpaRepository<SmartphoneEntity, Long> {
    List<SmartphoneEntity> findByBrand(String brand);
    List<SmartphoneEntity> findByNameLike(String pattern);
}
