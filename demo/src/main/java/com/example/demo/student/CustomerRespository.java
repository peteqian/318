package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Data Access Layer
@Repository
public interface CustomerRespository
        extends JpaRepository<Customer, Long> {

    // SELECT * FROM student WHERE email = ?
    // @Query("SELECT s FROM Student s WHERE s.email = ?")
    Optional<Customer> findCustomerByEmail(String email);
}
