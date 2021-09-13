package com.customergroup.customer;

import com.customergroup.application.domain.Contact;
import com.customergroup.data.ContactRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRespositoryTest {

    @Autowired
    private ContactRespository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void check_find_Contact_By_Email() {
        String email = "cake@gmail.com";
        Contact cake = new Contact(
                "Cake",
                "+61-412-123-456",
                "cake@gmail.com",
                "cake"
        );
        underTest.save(cake);

        // Optional<Contact> expected = underTest.findContactByEmail(email);
        // assertTrue(expected.isPresent());

        boolean expected = underTest.selectExistsEmail(email);
        assertTrue(expected);
    }

    @Test
    void check_does_not_find_contact_by_email() {
        String email = "invisible@gmail.com";
//        Optional<Contact> expected = underTest.findContactByEmail(email);
//        assertTrue(expected.isEmpty());
        boolean expected = underTest.selectExistsEmail(email);
        assertFalse(expected);
    }
}