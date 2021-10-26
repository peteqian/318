package com.businessgroup.application.controller;

import com.businessgroup.application.service.BusinessInteractiveQuery;
import com.businessgroup.domain.CustomerProduct;
import com.businessgroup.domain.TotalOrderValueCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessController {

    @Autowired
    BusinessInteractiveQuery businessInteractiveQuery;

    @GetMapping("/business/all")
    List<String> getAllOrderProducts(){
        return businessInteractiveQuery.getAllProductList();
    }

    @GetMapping("/business/{productName}/quantity")
    long getProductQuantityByName(@PathVariable String productName){
        return businessInteractiveQuery.getProductQuantity(productName);
    }

    @GetMapping("/business/{customerPhone}/product")
    List<String> getAllProductByCustomerPhone(@PathVariable String customerPhone){
        return businessInteractiveQuery.getAllProductsByCustomerID(customerPhone);
    }

    @GetMapping("/business/{customerPhone}/totalPrice")
    Double getTotalOrderValueByCustomerPhone(@PathVariable String customerPhone){
        return businessInteractiveQuery.getTotalOrderValueByCustomerID(customerPhone);
    }

    @GetMapping("/business/{customerPhone}/customerProduct/debug")
    List<CustomerProduct> getCustomerPhoneByCustomerPhoneDebug(
            @PathVariable String customerPhone){
        return businessInteractiveQuery.getCustomerDebugFunction(customerPhone);
    }

    @GetMapping("/business/{customerPhone}/totalOrder/debug")
    List<TotalOrderValueCustomer> getTotalOrderByCustomerPhoneDebug(
            @PathVariable String customerPhone){
        return businessInteractiveQuery.getTotalOrderDebugFunction(customerPhone);
    }

}
