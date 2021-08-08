package com.example.demo.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Service is responsible for logic
@Service
public class ContactService {

    private final ContactRespository contactRespository;

    @Autowired
    public ContactService(ContactRespository contactRespository) {
        this.contactRespository = contactRespository;
    }

    public List<Contact> getContacts(){
        return contactRespository.findAll();
    }

    public void addContact(Contact contact){

        System.out.println(contact);
        Optional<Contact> contactEmail = contactRespository.findContactByEmail(contact.getEmail());
        // SQL returns null but Boolean cannot be Null
        // Boolean contactEmail = contactRespository.findContactByEmail(contact.getEmail());
        if(contactEmail.isPresent()){
        // if(contactEmail){
            throw new IllegalStateException("Email already exists!");
        }
        contactRespository.save(contact);

    }

    public void deleteContact(Long contactId) {
        boolean exists = contactRespository.existsById(contactId);
        if(!exists){
            throw new IllegalStateException("Contact with id: " + contactId + " does not exist!");
        }
        contactRespository.deleteById(contactId);
    }

    // Moves entity into managed state
    @Transactional
    public void updateContact(long contactId, String name, String email) {
        Contact contact = contactRespository.findById(contactId)
                .orElseThrow(
                        () -> new IllegalStateException("Customer with id " + contactId + " does not exist!")
                );

        if (name != null && name.length() > 0 && !Objects.equals(contact.getName(), name)){
            contact.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(contact.getEmail(), email)){
            Optional<Contact> contactEmail = contactRespository.findContactByEmail(email);
            // boolean contactEmail = contactRespository.findContactByEmail(contact.getEmail());
            if(contactEmail.isPresent()){
            // if(contactEmail){
                throw new IllegalStateException("Email taken!");
            }
            contact.setEmail(email);
        }
    }
}
