package com.example.demo.contact;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ContactNotFoundException;
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

//        Optional<Contact> contactEmail = contactRespository.findContactByEmail(contact.getEmail());
//        if(contactEmail.isPresent()){
//            throw new BadRequestException("Email already exists!");
//        }

        Boolean contactEmail = contactRespository.selectExistsEmail(contact.getEmail());
        if(contactEmail){
            throw new BadRequestException("Email " + contact.getEmail() + " already exists!");
        }
        contactRespository.save(contact);
    }

    public void deleteContact(Long contactId) {
        boolean exists = contactRespository.existsById(contactId);
        if(!exists){
            throw new ContactNotFoundException("Contact with id: " + contactId + " does not exist!");
        }
        contactRespository.deleteById(contactId);
    }

    // Moves entity into managed state
    @Transactional
    public void updateContact(long contactId, String name, String email) {
        Contact contact = contactRespository.findById(contactId)
                .orElseThrow(
                        () -> new ContactNotFoundException("Customer with id " + contactId + " does not exist!")
                );

        if (name != null && name.length() > 0 && !Objects.equals(contact.getName(), name)){
            contact.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(contact.getEmail(), email)){
            Optional<Contact> contactEmail = contactRespository.findContactByEmail(email);
//             boolean contactEmail = contactRespository.findContactByEmail(contact.getEmail());
//             if(contactEmail){
//                throw new ContactNotFoundException("Email taken!");
//            }
            if(contactEmail.isPresent()){
                throw new ContactNotFoundException("Email taken!");
            }
            contact.setEmail(email);
        }
    }
}
