package com.ordergroup.controller;


import com.ordergroup.application.domain.Customer;
import com.ordergroup.application.domain.Orders;
import com.ordergroup.application.domain.Product;
import com.ordergroup.application.service.OrderService;
import com.ordergroup.application.service.OrdersEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path ="api/")
//API Layer
public class OrderController {

    private final OrderService orderService;
    private final OrdersEventService ordersEventService;


    @Autowired
    public OrderController(OrderService orderService, OrdersEventService ordersEventService){
        this.orderService = orderService;
        this.ordersEventService = ordersEventService;
    }

    //Get mappings

    @GetMapping(path = "/orders")
    public List<Orders> getOrders() {return orderService.getOrders();}

    @GetMapping(path = "/orders/{orderID}")
    public Orders getOrder(@PathVariable("orderID") long Id) { return orderService.getOrder(Id);}

    @GetMapping(path = "/orders/{orderID}/customer")
    public Customer getCustomerInfo(@PathVariable("orderID") long id) {return orderService.getCustomerInfo(id);}

    @GetMapping(path = "orders/{orderID}/product")
    public Product getProductInfo(@PathVariable("orderID")long id) {return orderService.getProductInfo(id);}


    //Post Mapping
    @PostMapping(path = "/orders")
    public void create(@RequestBody Map<String, String> values){
        orderService.create(Long.parseLong(values.get("custID")), values.get("productName"), Long.parseLong(values.get("quantity")));
    }
}
