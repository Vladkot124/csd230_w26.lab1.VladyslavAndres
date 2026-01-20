package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByIsbn(String isbn);

    BookEntity findById(long id);

    List<BookEntity> findByNameLike(String pattern);

    @Query("select b from BookEntity b where b.price between :min and :max")
    List<BookEntity> findBooksInPriceRange(double min, double max);
}
