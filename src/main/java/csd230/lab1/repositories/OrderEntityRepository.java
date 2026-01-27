package csd230.lab1.repositories;

import csd230.lab1.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM order_products WHERE product_id = :productId", nativeQuery = true)
    void deleteLinksForProduct(@Param("productId") Long productId);
}
