package com.customergroup.application.serivce;

import com.customergroup.domain.Customer;
import com.customergroup.domain.ICustomerValidator;
import com.customergroup.exception.CustomerNotFoundException;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.infrastructure.repository.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

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

        Customer custFound = customerRespository.getById(customerId);
        Map<String, String> data = new HashMap<String, String>();

        data.put("address", custFound.getAddress());
        data.put("phone", custFound.getContact().getPhone());

        return data;
    }
}
