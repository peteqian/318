package com.ordergroup.application.service;

import com.ordergroup.domain.Orders;
import com.ordergroup.domain.OrdersEvent;
import com.ordergroup.infrastructure.repository.OrdersEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrdersEventService {

    private final OrdersEventRepository ordersEventRepository;
    private RestTemplate restTemplate;

    @Autowired
    public OrdersEventService(OrdersEventRepository ordersEventRepository, RestTemplateBuilder builder){
        this.ordersEventRepository = ordersEventRepository;
        this.restTemplate = builder.build();
    }

    @EventListener
    public void handle(OrdersEvent ordersEvent){
        try {
            // save the orders Event
            System.out.println("Handle the published Event");
            ordersEventRepository.save(ordersEvent);

            //need to update stock
            System.out.println("Received order event, no cap");
            String getOrderURL = "http://localhost:8082/api/orders/"
                    + ordersEvent.getOrderID();
            System.out.println(getOrderURL);

            Orders order = restTemplate.getForObject(getOrderURL, Orders.class);

            String updateStockUrl = "http://localhost:8081/product/"
                    + order.getProductName()
                    + "/quantity/"
                    + order.getQuantity();
            System.out.println(updateStockUrl);
            restTemplate.exchange(updateStockUrl, HttpMethod.PUT,
                    new HttpEntity<>(order, new HttpHeaders()), Void.class);

        } catch(Exception e) {
            System.out.println("Message: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e
            );
        }
    }

}
