package com.example.demo.customer;

import com.example.demo.contact.Contact;
import com.example.demo.customer.CustomerRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRespositoryTest {

    @Autowired
    private CustomerRespository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void check_find_company_by_name() {
        String companyName = "Some Company Name";
        Customer customerOne = new Customer(
                "Some Company Name",
                "Some address",
                "Australia"
        );
        underTest.save(customerOne);
        boolean expected = underTest.selectExistingCompany(companyName);
        assertTrue(expected);
    }

    @Test
    void check_does_not_find_company_by_name() {
        String companyName = "Non-existing Company Name";
        boolean expected = underTest.selectExistingCompany(companyName);
        assertFalse(expected);
    }

}
