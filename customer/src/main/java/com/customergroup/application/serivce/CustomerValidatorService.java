package com.customergroup.application.serivce;

import com.customergroup.domain.Customer;
import com.customergroup.domain.ICustomerValidator;
import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.ContactNotFoundException;
import com.customergroup.exception.CustomerNotFoundException;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.infrastructure.repository.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerValidatorService implements ICustomerValidator {

    private final CustomerRespository customerRespository;
    private final ContactRespository contactRespository;

    @Autowired
    public CustomerValidatorService(CustomerRespository customerRespository,
                           ContactRespository contactRespository){
        this.customerRespository = customerRespository;
        this.contactRespository = contactRespository;
    }

    // Implementation of Domain Service
    @Override
    public Map<String, String> validateCustomer(long customerId) {
        boolean exists = customerRespository.existsById(customerId);
        if (!exists) {
            throw new CustomerNotFoundException("Company with id " + customerId + " does not exist!");
        }

        Customer customer = customerRespository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer with id " + customerId + " does not exist!")
                );

        if (customer.getContact() == null){
            throw new ContactNotFoundException("The customer with id: " + customerId +
                    " does not have any contact details.");
        }

        Customer custFound = customerRespository.getById(customerId);
        Map<String, String> data = new HashMap<String, String>();

        data.put("address", custFound.getAddress());
        data.put("phone", custFound.getContact().getPhone());

        return data;
    }
}
