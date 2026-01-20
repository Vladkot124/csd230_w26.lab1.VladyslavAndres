package csd230.lab1.repositories;

import csd230.lab1.entities.SmartphoneEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SmartphoneRepositoryTest {

    @Autowired
    private SmartphoneRepository smartphoneRepository;

    @Test
    void crud_smartphone() {
        SmartphoneEntity s = new SmartphoneEntity("Phone", "Desc", 999.99, "BrandX", 24, 256, true);

        s = smartphoneRepository.save(s);
        assertNotNull(s.getId());

        SmartphoneEntity found = smartphoneRepository.findById(s.getId()).orElse(null);
        assertNotNull(found);

        found.setStorageGb(512);
        smartphoneRepository.save(found);

        SmartphoneEntity updated = smartphoneRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals(512, updated.getStorageGb());

        smartphoneRepository.delete(updated);
        assertTrue(smartphoneRepository.findById(updated.getId()).isEmpty());
    }
}
