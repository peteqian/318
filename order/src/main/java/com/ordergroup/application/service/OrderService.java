package com.ordergroup.application.service;

import com.ordergroup.application.domain.Orders;
import com.ordergroup.application.domain.OrdersEvent;
import com.ordergroup.data.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private RestTemplate restTemplate;

    private ApplicationEventPublisher publisher;

    @Autowired
    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public List<Orders> getOrders(){
        return orderRepository.findAll();
    }

    public Orders getOrder(long orderID){
        return orderRepository.findById(orderID)
                .orElseThrow( () -> new RuntimeException("Cannot find a contact by the id: " + orderID));
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void create(long custID, String productName, long quanitity){

        // validate customer
        String validateURL = ""

        // check product inventory

        // order Event
        //create an order
        Orders order;
        //order Event
        OrdersEvent ordersEvent = new OrdersEvent(order);
        publisher.publishEvent(ordersEvent);

            // update product stock
    }

}
