package com.customergroup.controller;

import com.customergroup.application.domain.Contact;
import com.customergroup.application.domain.Customer;
import com.customergroup.application.service.ContactService;
import com.customergroup.application.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/")
// API Layer
public class CustomerController {

    private final CustomerService customerService;
    private final ContactService contactService;

    @Autowired
    public CustomerController(CustomerService customerService,
                              ContactService contactService){
        this.customerService = customerService;
        this.contactService = contactService;
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
        return contactService.getContacts();
    }

    // GET One Contact
    @GetMapping(path = "/contact/get/{contactId}")
    public Optional<Contact> getContact(@PathVariable("contactId") Long contactId){
        return contactService.getContact(contactId);
    }
    // POST
    @PostMapping(path = "/contact")
    public void registerNewCustomer(@RequestBody Contact contact){
        contactService.addContact(contact);
    }

    // DELETE
    // Path Variable creates a /studentId within the API
    @DeleteMapping(path = "/contact/{customerId}")
    public void deleteStudent(@PathVariable("customerId") Long customerId){
        contactService.deleteContact(customerId);
    }

    // PUT
    @PutMapping(path = "/contact/{customerId}")
    public void updateContact(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String position){
        contactService.updateContact(customerId, name, phone, email, position);
    }

    @PutMapping(path = "/contact/putRaw/{customerId}")
    public void updateContact(@RequestBody Contact contact){
        contactService.updateContactByRaw(contact);
    }

    //PUT
    @PutMapping(path = "/customer/{id}/contact/{contactId}")
    public void updateCustomerContactDetails(@PathVariable Long id,
                                          @PathVariable Long contactId){
        customerService.updateCustomerContactDetails(id, contactId);
    }
}

