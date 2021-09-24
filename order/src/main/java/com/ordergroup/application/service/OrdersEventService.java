package com.ordergroup.application.service;

import com.ordergroup.application.domain.Orders;
import com.ordergroup.application.domain.OrdersEvent;
import com.ordergroup.data.OrdersEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
        ordersEventRepository.save(ordersEvent);

        //need to update stock
        System.out.println("Received order event, no cap");
        String getOrderURL = "http://localhost:8082/api/orders/" + ordersEvent.getOrderID();
        System.out.println(getOrderURL);

        Orders order = restTemplate.getForObject(getOrderURL, Orders.class);

        String updateStockUrl = "http://localhost:8081/product/" + order.getProductName() +"/quantity/"+order.getQuantity();
        System.out.println(updateStockUrl);
        restTemplate.exchange(updateStockUrl, HttpMethod.PUT, new HttpEntity<>(order, new HttpHeaders()), Void.class);
    }

}
