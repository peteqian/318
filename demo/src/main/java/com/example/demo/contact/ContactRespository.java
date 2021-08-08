package com.example.demo.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// Data Access Layer
@Repository
public interface ContactRespository
        extends JpaRepository<Contact, Long> {

    @Query("SELECT s FROM Contact s WHERE s.email = ?1")
    Optional<Contact> findContactByEmail(String email);

//    @Query("SELECT s FROM Contact s WHERE s.email = ?1")
//    boolean findContactByEmail(String email);
}
