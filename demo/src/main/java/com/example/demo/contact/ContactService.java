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

    public Optional<Contact> getContact(Long contactId){
        return contactRespository.findById(contactId);
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
    public void updateContact(long contactId, String name, String phone, String email, String position) {
        Contact contact = contactRespository.findById(contactId)
                .orElseThrow(
                        () -> new ContactNotFoundException("Customer with id " + contactId + " does not exist!")
                );

        if (name != null && name.length() > 0 && !Objects.equals(contact.getName(), name)){
            contact.setName(name);
        }

//        if(email != null && email.length() > 0 && !Objects.equals(contact.getEmail(), email)){
//            Optional<Contact> contactEmail = contactRespository.findContactByEmail(email);
//            if(contactEmail.isPresent()){
//                throw new ContactNotFoundException("Email '" + email + "' is already taken!");
//            }
        boolean emailExists = contactRespository.selectExistsEmail(email);
        System.out.println("Email you're trying to insert: " + email);                                                  // Debug
        System.out.println("Email of the current person you're inserting: " + contact.getEmail());                      // Debug
        System.out.println("Looking for any users with the same email: " + emailExists);                                // Debug
        if(contact.getEmail().equals(email)){
            throw new BadRequestException("This is the same email.");
        }

        if(emailExists){
            throw new BadRequestException("Email '" + email + "' is already taken!");
        }
        contact.setEmail(email);
    }

    @Transactional
    public void updateContactByRaw(Contact contact){
        Contact resposContact = contactRespository.findById(contact.getId())
                .orElseThrow(
                        () -> new ContactNotFoundException("Customer with id " + contact.getId() + " does not exist!")
                );
        if (contact.getName() != null && contact.getName().length() > 0 && !Objects.equals(resposContact.getName(), contact.getName())){
            resposContact.setName(contact.getName());
        }
        boolean emailExists = contactRespository.selectExistsEmail(contact.getEmail());
        System.out.println("Email you're trying to insert: " + contact.getEmail());                                               // Debug
        System.out.println("Email of the current person you're inserting: " + resposContact.getEmail());                      // Debug
        System.out.println("Looking for any users with the same email: " + emailExists);                                // Debug
        if(emailExists){
            throw new BadRequestException("Email '" + contact.getEmail() + "' is already taken!");
        }

        if (contact.getEmail() != null && contact.getEmail().length() > 0){
            System.out.println("Hello");
            System.out.println(resposContact.getEmail());
            System.out.println(contact.getEmail());
            resposContact.setEmail(contact.getEmail());
        }

        if (contact.getPhone() != null && contact.getPhone().length() > 0){
            resposContact.setPhone(contact.getPhone());
        }

        if (contact.getPosition() != null && contact.getPosition().length() > 0){
            resposContact.setPosition(contact.getPosition());
        }
    }
}
