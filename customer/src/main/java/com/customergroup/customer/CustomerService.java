package com.customergroup.customer;

import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.ContactNotFoundException;
import com.customergroup.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRespository customerRespository;
    private final ContactRespository contactRespository;

    @Autowired
    public CustomerService(CustomerRespository customerRespository,
                           ContactRespository contactRespository){
        this.customerRespository = customerRespository;
        this.contactRespository = contactRespository;
    }

    public List<Customer> getCustomers(){
        return customerRespository.findAll();
    }

    public Optional<Customer> getCustomerById(long id){
        return customerRespository.findById(id);
    }

    public Optional<Customer> getCustomer(String companyName){
        return customerRespository.findCustomerByCompanyName(companyName);
    }

    public void addCustomer(Customer customer){
        boolean customerByCompanyName
                = customerRespository.selectExistingCompany(customer.getCompanyName());
        if(customerByCompanyName){
            throw new BadRequestException("Company Name: " + customer.getCompanyName() + " already exists!");
        }
        customerRespository.save(customer);
    }

    public void deleteCustomer(Long customerId){

        boolean exists = customerRespository.existsById(customerId);
        if(!exists){
            throw new CustomerNotFoundException("Company with id " + customerId + " does not exist!");
        }
        customerRespository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(long customerId, String companyName, String address, String country) {
        Customer customer = customerRespository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer with id " + customerId + " does not exist!")
                );

        if (companyName != null && companyName.length() > 0 && !Objects.equals(customer.getCompanyName(), companyName)){
            boolean companyExists = customerRespository.selectExistingCompany(companyName);
            if(companyExists){
                throw new BadRequestException("Company Name: '" + companyName + "' is already taken!");
            }
            customer.setCompanyName(companyName);
        }

        if(address != null && address.length() > 0 ){
            customer.setAddress(address);
        }

        if(country != null && country.length() > 0 ){
            customer.setCountry(country);
        }
    }

    @Transactional
    public void updateCustomerByRaw(Customer customer) {
        Customer reposCustomer = customerRespository.findById(customer.getId())
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer with id " + customer.getId() + " does not exist!")
                );

        if (customer.getCompanyName() != null && customer.getCompanyName().length() > 0){

            boolean companyExists = customerRespository.selectExistingCompany(customer.getCompanyName());
            if(companyExists){
                throw new BadRequestException("Company Name: '" + customer.getCompanyName() + "' is already taken!");
            }
            reposCustomer.setCompanyName(customer.getCompanyName());
        }

        if(customer.getAddress() != null && customer.getAddress().length() > 0 ){
            reposCustomer.setAddress(customer.getAddress());
        }

        if(customer.getCountry() != null && customer.getCountry().length() > 0 ){
            reposCustomer.setCountry(customer.getCountry());
        }
    }

    public List<Contact> getContacts(){
        return contactRespository.findAll();
    }


    public Optional<Contact> getContact(Long contactId){
        /**
         * Returns a list of Contacts
         */
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

        if (name != null && name.length() > 0){
            contact.setName(name);
        }

        boolean emailExists = contactRespository.selectExistsEmail(email);

        if(emailExists){
            throw new BadRequestException("Email '" + email + "' is already taken!");
        }

        if (email != null && email.length() > 0){
            contact.setEmail(email);
        }

        if (phone != null && phone.length() > 0){
            contact.setPhone(phone);
        }

        if (position != null && position.length() > 0){
            contact.setPosition(position);
        }

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
