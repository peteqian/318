package com.customergroup.infrastructure.repository;

import com.customergroup.domain.Contact;
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

    @Query("" + "SELECT CASE WHEN COUNT(email) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Contact s " +
            "WHERE lower(s.email) = ?1"
    )
    Boolean selectExistsEmail(String email);

    @Query("" +
            "SELECT CASE WHEN COUNT(phone) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Contact s " +
            "WHERE s.phone = ?1"
    )
    Boolean selectExistsPhone(String phone);

    @Query("SELECT s FROM Contact s WHERE lower(s.phone) = ?1")
    Contact findContactByPhone(String phone);
}
