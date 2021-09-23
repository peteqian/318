package com.productgroup.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.productgroup.domain.Product;

import java.util.Optional;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productName = ?1")
    Optional<Product> findProductByName(String productName);

    @Query("" +
            "SELECT CASE WHEN COUNT(productName) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Product p " +
            "WHERE p.productName = ?1"
    )
    Boolean findExistingProduct(String productName);
}
