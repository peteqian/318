package com.example.demo.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRespository
        extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCompanyName(String companyName);
}
