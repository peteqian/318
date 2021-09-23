package com.customergroup.application.serivce;

import com.customergroup.domain.Contact;
import com.customergroup.domain.Customer;

import com.customergroup.domain.ICustomerValidator;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.infrastructure.repository.CustomerRespository;

import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.ContactFailedException;
import com.customergroup.exception.ContactNotFoundException;
import com.customergroup.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CustomerService implements ICustomerValidator {

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

    public Customer getCustomerById(long id){
        return customerRespository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cannot a company with the id: " + id));
    }

    public Customer getCustomer(String companyName){
        return customerRespository.findCustomerByCompanyName(companyName)
                .orElseThrow(()-> new RuntimeException("Cannot find the company name: " + companyName));
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

    @Transactional
    public void updateCustomerContactDetails(long customerId, long contactId){
        Customer customer = customerRespository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id " + customerId + " does not exist!")
        );
        Contact contact = contactRespository.findById(contactId).orElseThrow(
                () -> new ContactNotFoundException("Customer with id " + contactId + " does not exist!")
        );

        // If the contact details have already been assigned to the Customer.
        if(contact.getAssigned() != -1){
            // Throw an error
            throw new ContactFailedException(contactId);
        } else {

            // Otherwise, get the contact details from the current Customer.
            if(customer.getContact() != null){
                System.out.println("The current customer's contact details isn't empty.");

                // Clear the product detail assignment and set the product to null.
                Contact currentContact = customer.getContact();
                currentContact.setAssigned(-1);
                currentContact.setCustomer(null);

                System.out.println("Successfully cleared the contact's customer details.");
            }
            // set the Product to new details and assign the productdetail to new product.
            customer.setContact(contact);
            contact.setAssigned(customerId);
            System.out.println("Assigned new contact details successfully.");
        }
    }

    @Transactional
    public void removeCustomerContactDetails(long customerId, long contactId){
        Customer customer = customerRespository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id " + customerId + " does not exist!")
        );
        Contact contact = contactRespository.findById(contactId).orElseThrow(
                () -> new ContactNotFoundException("Customer with id " + contactId + " does not exist!")
        );

        if(Objects.equals(customer.getContact().getId(), contact.getId())){
            customer.setContact(null);
            contact.setAssigned(-1);
            contact.setCustomer(null);
        } else {
            throw new RuntimeException("Contact details does not belong to this customer.");
        }
    }

    // Implementation of Domain Service
    @Override
    public Map<String, String> validateCustomer(long customerId) {
        boolean exists = customerRespository.existsById(customerId);
        if (!exists) {
            throw new CustomerNotFoundException("Company with id " + customerId + " does not exist!");
        }

        Customer custFound = customerRespository.getById(customerId);
        Map<String, String> data = new HashMap<String, String>();

        data.put("address", custFound.getAddress());
        data.put("phone", custFound.getContact().getPhone());

        return data;
    }
}
