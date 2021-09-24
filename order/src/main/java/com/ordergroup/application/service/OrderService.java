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
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private RestTemplate restTemplate;

    private ApplicationEventPublisher publisher;

    @Autowired
    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher publisher, RestTemplateBuilder builder) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
        this.restTemplate = builder.build();
    }

    public List<Orders> getOrders(){
        return orderRepository.findAll();
    }

    public Orders getOrder(long orderID){
        return orderRepository.findById(orderID)
                .orElseThrow( () -> new RuntimeException("Cannot find a contact by the id: " + orderID));
    }

    public void create(long custID, String productName, long quanitity){
        // validate customer
        String validateURL = "http://localhost:8080/api/customer/validate="+custID;
        Map<String, String> cusBasicDetails = restTemplate.getForObject(validateURL, Map.class);

        // check product inventory
        String checkInvURL = "http://localhost:8081/product/checkInventory/productName="+productName+"/quantity="+quanitity;
        Map<String, String> prodDetails = restTemplate.getForObject(checkInvURL, Map.class);

        double totalPrice = (Double.parseDouble(prodDetails.get("Price")) * quanitity);
        // order Event
        //create an order
        Orders order = new Orders(prodDetails.get("Supplier")
                                , productName
                                , quanitity
                                , totalPrice
                                , cusBasicDetails.get("address")
                                , cusBasicDetails.get("phone"));

        orderRepository.save(order);
        //order Event
        OrdersEvent ordersEvent = new OrdersEvent(order);
        publisher.publishEvent(ordersEvent);
    }

}
