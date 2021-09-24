package com.ordergroup.application.service;

import com.ordergroup.application.domain.OrdersEvent;
import com.ordergroup.data.OrdersEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrdersEventService {

    private final OrdersEventRepository ordersEventRepository;

    @Autowired
    public OrdersEventService(OrdersEventRepository ordersEventRepository){
        this.ordersEventRepository = ordersEventRepository;
    }

    @EventListener
    public void handle(OrdersEvent ordersEvent){
        ordersEventRepository.save(ordersEvent);

        //need to update stock
        System.out.println("Recieved order event, no cap");
    }

}
