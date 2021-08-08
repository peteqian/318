package com.example.demo.contact;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRespositoryTest {

    @Autowired
    private ContactRespository underTest;

    @Test
    void checkfindContactByEmail() {
        // Given
        String email = "cake@gmail.com";
        Contact cake = new Contact(
                "Cake",
                "+61-412-123-456",
                "cake@gmail.com",
                "cake",
                LocalDate.of(1995,4,28)
        );
        underTest.save(cake);
        // When
        Optional<Contact> expected = underTest.findContactByEmail(email);
    }
}