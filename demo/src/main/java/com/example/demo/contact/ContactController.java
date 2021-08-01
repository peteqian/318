package com.example.demo.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/contact")                                                                                // Create API Layer

public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    // GET
    @GetMapping
    public List<Contact> getContacts(){
        return contactService.getContacts();
    }

    // POST
    @PostMapping
    public void registerNewCustomer(@RequestBody Contact contact){
        contactService.addContact(contact);
    }

    // DELETE
    // Path Variable creates a /studentId within the API
    @DeleteMapping(path = "{customerId}")
    public void deleteStudent(@PathVariable("customerId") Long customerId){
        contactService.deleteContact(customerId);
    }

    // PUT
    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        contactService.updateContact(customerId, name, email);
    }

}
