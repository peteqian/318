package com.example.demo.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Data Access Layer
@Repository
public interface ContactRespository
        extends JpaRepository<Contact, Long> {

    // SELECT * FROM student WHERE email = ?
    // @Query("SELECT s FROM Student s WHERE s.email = ?")
    Optional<Contact> findContactByEmail(String email);
}
