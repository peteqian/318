package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")                                                                                // Create API Layer

public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    // GET
    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    // POST
    @PostMapping
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    // DELETE
    // Path Variable creates a /studentId within the API
    @DeleteMapping(path = "{customerId}")
    public void deleteStudent(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    // PUT
    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        customerService.updateCustomer(customerId, name, email);
    }

}
