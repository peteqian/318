package com.ordergroup.infrastructure;

import com.ordergroup.domain.OrdersEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersEventRepository
        extends JpaRepository<OrdersEvent, Long> {
}