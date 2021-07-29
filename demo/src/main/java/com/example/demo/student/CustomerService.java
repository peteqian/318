package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Service is responsible for logic
@Service
public class CustomerService {

    private final CustomerRespository customerRespository;

    @Autowired
    public CustomerService(CustomerRespository customerRespository) {
        this.customerRespository = customerRespository;
    }

    public List<Customer> getCustomers(){
        return customerRespository.findAll();
    }

    public void addCustomer(Customer customer){

        System.out.println(customer);
        Optional<Customer> customerEmail = customerRespository.findCustomerByEmail(customer.getEmail());
        if(customerEmail.isPresent()){
            throw new IllegalStateException("Email already exists!");
        }
        customerRespository.save(customer);

    }

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRespository.existsById(customerId);
        if(!exists){
            throw new IllegalStateException("Customer with id: " + customerId + " does not exist!");
        }
        customerRespository.deleteById(customerId);
    }

    // Moves entity into managed state
    @Transactional
    public void updateCustomer(long customerId, String name, String email) {
        Customer customer = customerRespository.findById(customerId)
                .orElseThrow(
                        () -> new IllegalStateException("Customer with id " + customerId + " does not exist!")
                );

        if (name != null && name.length() > 0 && !Objects.equals(customer.getName(), name)){
            customer.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)){
            Optional<Customer> customerOptional = customerRespository.findCustomerByEmail(email);
            if(customerOptional.isPresent()){
                throw new IllegalStateException("Email taken!");
            }
            customer.setEmail(email);
        }
    }
}
