package com.example.demo.customer;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ContactNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
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

    public Optional<Customer> getCustomerById(long id){
        return customerRespository.findById(id);
    }

    public Optional<Customer> getCustomer(String companyName){
        return customerRespository.findCustomerByCompanyName(companyName);
    }

    public void addCustomer(Customer customer){
//        Optional<Customer> customerByCompanyName
//                = customerRespository.findCustomerByCompanyName(
//                        customer.getCompanyName()
//        );
//        if(customerByCompanyName.isPresent()){
//            throw new BadRequestException("Company Name already exists!");
//        }
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

}
