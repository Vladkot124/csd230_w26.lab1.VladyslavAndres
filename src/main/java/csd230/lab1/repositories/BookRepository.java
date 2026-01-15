package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // REQUIRED by lab (make sure BookEntity has a field named "isbn")
    List<BookEntity> findByIsbn(String isbn);

    // REQUIRED by lab (even though JpaRepository already has findById returning Optional)
    BookEntity findById(long id);

    // REQUIRED "Like" query (use pattern like "%drew%")
    List<BookEntity> findByTitleLike(String pattern);

    // REQUIRED custom @Query (price is inherited from PublicationEntity)
    @Query("select b from BookEntity b where b.price between :min and :max")
    List<BookEntity> findBooksInPriceRange(@Param("min") double min, @Param("max") double max);
}
