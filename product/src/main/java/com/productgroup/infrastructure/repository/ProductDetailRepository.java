package com.productgroup.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.productgroup.domain.ProductDetail;

@Repository
public interface ProductDetailRepository
        extends JpaRepository<ProductDetail, Long> {
}
