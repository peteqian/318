package com.customergroup.application.service;

import com.customergroup.application.domain.Contact;
import com.customergroup.data.ContactRespository;
import com.customergroup.application.domain.Customer;
import com.customergroup.data.CustomerRespository;
import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.ContactFailedException;
import com.customergroup.exception.ContactNotFoundException;
import com.customergroup.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void updateCustomerContactDetails(long customerId, long contactId){
        Customer customer = customerRespository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id " + customerId + " does not exist!")
        );
        Contact contact = contactRespository.findById(contactId).orElseThrow(
                () -> new ContactNotFoundException("Customer with id " + contactId + " does not exist!")
        );
        if(contact.getAssigned() != -1) throw new ContactFailedException(contactId);
        contact.setAssigned(customerId);
        customer.setContact(contact);
    }

    @Transactional
    public String[] getCustomerDetails(long customerId){
        Customer customer = customerRespository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id " + customerId + " does not exist!")
        );
        String[] details = {customer.getAddress(), customer.getContact().getPhone()};
        return details;
    }
}
