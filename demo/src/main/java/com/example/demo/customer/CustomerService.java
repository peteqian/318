package com.example.demo.customer;

import com.example.demo.contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRespository customerRespository;

    @Autowired
    public CustomerService(CustomerRespository customerRespository){
        this.customerRespository = customerRespository;
    }

    public List<Customer> getCustomers(){
        return customerRespository.findAll();
    }

    public void addCustomer(Customer customer){
        System.out.println(customer);
        Optional<Customer> customerByCompanyName
                = customerRespository.findCustomerByCompanyName(
                        customer.getCompanyName()
        );
        if(customerByCompanyName.isPresent()){
            throw new IllegalStateException("Company Name already exists!");
        }
        customerRespository.save(customer);
    }

    public void deleteCustomer(Long customerCompanyName){
        boolean exists = customerRespository.existsById(customerCompanyName);
        if(!exists){
            throw new IllegalStateException("Company " + customerCompanyName + " does not exist!");
        }
    }

    @Transactional
    public void updateCustomer(long customerId, String name, String address, String country) {
        Customer customer = customerRespository.findById(customerId)
                .orElseThrow(
                        () -> new IllegalStateException("Customer with id " + customerId + " does not exist!")
                );

        if (name != null && name.length() > 0 && !Objects.equals(customer.getCompanyName(), name)){
            customer.setCompanyName(name);
        }

        if(address != null && address.length() > 0 ){
            customer.setAddress(address);
        }

        if(country != null && country.length() > 0 ){
            customer.setCountry(country);
        }
    }

}
