package csd230.lab1.repositories;

import csd230.lab1.entities.LaptopEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LaptopRepositoryTest {

    @Autowired
    private LaptopRepository laptopRepository;

    @Test
    void crud_laptop() {
        LaptopEntity l = new LaptopEntity("Laptop", "Desc", 1500.00, "BrandY", 24, 16, "Intel i7");

        l = laptopRepository.save(l);
        assertNotNull(l.getId());

        LaptopEntity found = laptopRepository.findById(l.getId()).orElse(null);
        assertNotNull(found);

        found.setRamGb(32);
        laptopRepository.save(found);

        LaptopEntity updated = laptopRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals(32, updated.getRamGb());

        laptopRepository.delete(updated);
        assertTrue(laptopRepository.findById(updated.getId()).isEmpty());
    }
}
