package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthorContainingIgnoreCase(String author);
    List<BookEntity> findByIsbnContainingIgnoreCase(String isbn);
}
