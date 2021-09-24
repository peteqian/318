package com.ordergroup.data;

import com.ordergroup.application.domain.OrdersEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersEventRepository
        extends JpaRepository<OrdersEvent, Long> {
}