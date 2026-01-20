package csd230.lab1.repositories;

import csd230.lab1.entities.DiscMagEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DiscMagRepositoryTest {

    @Autowired
    private DiscMagRepository discMagRepository;

    @Test
    void crud_discmag() {
        DiscMagEntity d = new DiscMagEntity("DiscMag", "Desc", 19.99, 2, "Pub", "DVD");

        d = discMagRepository.save(d);
        assertNotNull(d.getId());

        DiscMagEntity found = discMagRepository.findById(d.getId()).orElse(null);
        assertNotNull(found);

        found.setDiscType("CD");
        discMagRepository.save(found);

        DiscMagEntity updated = discMagRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals("CD", updated.getDiscType());

        discMagRepository.delete(updated);
        assertTrue(discMagRepository.findById(updated.getId()).isEmpty());
    }
}
