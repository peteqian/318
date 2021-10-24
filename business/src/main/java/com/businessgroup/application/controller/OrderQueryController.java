package com.businessgroup.application.controller;

import com.businessgroup.application.service.OrderInteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderQueryController {

    @Autowired
    OrderInteractiveQuery orderInteractiveQuery;

    @GetMapping("/business/all")
    List<String> getAllOrderProducts(){
        return orderInteractiveQuery.getAllProductList();
    }

}
