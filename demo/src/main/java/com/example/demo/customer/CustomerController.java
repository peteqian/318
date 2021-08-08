package com.example.demo.customer;

import com.example.demo.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/customer")

// API Layer
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(path = "/get/{customerCompanyName}")
    public Optional<Customer> getCustomer(@PathVariable("customerCompanyName") String customerCompanyName){
        return customerService.getCustomer(customerCompanyName);
    }


    @PostMapping
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteStudent(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String country){
        customerService.updateCustomer(customerId, name, address, country);
    }
}
