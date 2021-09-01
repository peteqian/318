package com.customergroup.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRespository
        extends JpaRepository<Customer, Long> {
    @Query("SELECT s FROM Customer s WHERE s.companyName = ?1")
    Optional<Customer> findCustomerByCompanyName(String companyName);

    @Query("" +
            "SELECT CASE WHEN COUNT(companyName) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Customer s " +
            "WHERE s.companyName = ?1"
    )
    Boolean selectExistingCompany(String companyName);

}
