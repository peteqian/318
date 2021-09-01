package com.customergroup.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/")

// API Layer
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping(path = "/customer")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(path = "/customer/get/{customerId}")
    public Optional<Customer> getCustomer(@PathVariable("customerId") long Id){
        return customerService.getCustomerById(Id);
    }

    @GetMapping(path = "/customer/get/companyName={customerCompanyName}")
    public Optional<Customer> getCustomer(@PathVariable("customerCompanyName") String customerCompanyName){
        return customerService.getCustomer(customerCompanyName);
    }

    @PostMapping(path = "/customer")
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @DeleteMapping(path = "/customer/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "/customer/{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String country){
        customerService.updateCustomer(customerId, companyName, address, country);
    }

    @PutMapping(path = "/customer/putRaw/{customerId}")
    public void updateCustomerByRaw(@RequestBody Customer customer){
        customerService.updateCustomerByRaw(customer);
    }

    // GET
    @GetMapping(path = "/contact")
    public List<Contact> getContacts(){
        return customerService.getContacts();
    }

    // GET One Contact
    @GetMapping(path = "/contact/get/{contactId}")
    public Optional<Contact> getContact(@PathVariable("contactId") Long contactId){
        return customerService.getContact(contactId);
    }
    // POST
    @PostMapping(path = "/contact")
    public void registerNewCustomer(@RequestBody Contact contact){
        customerService.addContact(contact);
    }

    // DELETE
    // Path Variable creates a /studentId within the API
    @DeleteMapping(path = "/contact/{customerId}")
    public void deleteStudent(@PathVariable("customerId") Long customerId){
        customerService.deleteContact(customerId);
    }

    // PUT
    @PutMapping(path = "/contact/{customerId}")
    public void updateContact(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String position){
        customerService.updateContact(customerId, name, phone, email, position);
    }

    @PutMapping(path = "/contact/putRaw/{customerId}")
    public void updateContact(@RequestBody Contact contact){
        customerService.updateContactByRaw(contact);
    }

}
