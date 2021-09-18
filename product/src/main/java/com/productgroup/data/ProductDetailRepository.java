package com.productgroup.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.productgroup.application.domain.ProductDetail;

@Repository
public interface ProductDetailRepository
        extends JpaRepository<ProductDetail, Long> {
}
