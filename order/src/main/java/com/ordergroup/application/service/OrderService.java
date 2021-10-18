package com.ordergroup.application.service;

import com.ordergroup.domain.Product;
import com.ordergroup.domain.Customer;
import com.ordergroup.domain.Orders;
import com.ordergroup.domain.OrdersEvent;
import com.ordergroup.infrastructure.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private RestTemplate restTemplate;
    private ApplicationEventPublisher publisher;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ApplicationEventPublisher publisher,
                        RestTemplateBuilder builder) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
        this.restTemplate = builder.build();
    }

    public List<Orders> getOrders(){
        return orderRepository.findAll();
    }

    public Orders getOrder(long orderID){
        return orderRepository.findById(orderID)
                .orElseThrow( () ->
                        new RuntimeException("Cannot find a order by the id: "
                                + orderID));
    }

    public Customer getCustomerInfo(long orderID){
        /*
        2 Ways to get this method done.
        1. Create a Contact Class and call for 2 API endpoints, one refers to
         customer and one to contact.

        2. Call for an API endpoint to Customer where the API returns a MAP
        containing
        key:            value:
        address         something
        phone           something
         */

        Orders orderObj = orderRepository.findById(orderID).orElseThrow(
                () -> new RuntimeException("Cannot find a order by the id: "
                        + orderID));

        String customerNum = orderObj.getCusPhoneNum();
        String getCustomer =
                "http://localhost:8080/api/customer/phone=" + customerNum;
        Customer customer = restTemplate.getForObject(getCustomer,
                Customer.class);
        return customer;

    }

    public Product getProductInfo(long orderID){
        Orders orderObj = orderRepository.findById(orderID).orElseThrow(
                () -> new RuntimeException("Cannot find a order by the id: "
                        + orderID)
        );
        String prodName = orderObj.getProductName();
        String getProdURL = "http://localhost:8081/product/name=" + prodName;
        Product prod = restTemplate.getForObject(getProdURL, Product.class);
        return prod;
    }

    public void create(long custID, String productName, long quanitity){

        try {
            // validate customer
            String validateURL = "http://localhost:8080/api/customer/validate=" + custID;
            Customer customer = restTemplate.getForObject(validateURL, Customer.class);

            // check product inventory
            String checkInvURL = "http://localhost:8081/product/checkInventory/productName=" + productName + "/quantity=" + quanitity;
            Product product = restTemplate.getForObject(checkInvURL, Product.class);

            double totalPrice = (Double.parseDouble(product.getPrice()) * quanitity);
            // order Event
            //create an order
            Orders order = new Orders(product.getSupplier()
                    , productName
                    , quanitity
                    , totalPrice
                    , customer.getAddress()
                    , customer.getPhone()
            );

            orderRepository.save(order);
            //order Event
            OrdersEvent ordersEvent = new OrdersEvent(order);
            publisher.publishEvent(ordersEvent);
        }
        catch (Exception e){
            System.out.println("Message: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e
            );
        }
    }

}
