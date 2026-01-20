package csd230.lab1.repositories;

import csd230.lab1.entities.MagazineEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MagazineRepositoryTest {

    @Autowired
    private MagazineRepository magazineRepository;

    @Test
    void crud_magazine() {
        MagazineEntity m = new MagazineEntity("Mag", "Desc", 9.99, 1, "Pub");

        m = magazineRepository.save(m);
        assertNotNull(m.getId());

        MagazineEntity found = magazineRepository.findById(m.getId()).orElse(null);
        assertNotNull(found);

        found.setPublisher("UpdatedPub");
        magazineRepository.save(found);

        MagazineEntity updated = magazineRepository.findById(found.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals("UpdatedPub", updated.getPublisher());

        magazineRepository.delete(updated);
        assertTrue(magazineRepository.findById(updated.getId()).isEmpty());
    }
}
