package com.customergroup.domain;

import java.util.Map;

public interface ICustomerValidator {
    public Map<String, String> validateCustomer(long customerId);
}
