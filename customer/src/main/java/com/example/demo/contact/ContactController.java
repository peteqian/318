package com.example.demo.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // GET One Contact
    @GetMapping(path = "/get/{contactId}")
    public Optional<Contact> getContact(@PathVariable("contactId") Long contactId){
        return contactService.getContact(contactId);
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
    public void updateContact(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String position){
        contactService.updateContact(customerId, name, phone, email, position);
    }

    @PutMapping(path = "/putRaw/{customerId}")
    public void updateContact(@RequestBody Contact contact){
        contactService.updateContactByRaw(contact);
    }

}
