package com.example.demo.contact;

import com.example.demo.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRespository contactRespository;
    private ContactService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ContactService(contactRespository);
    }

    @Test
    void getAllContacts(){
        underTest.getContacts();
        verify(contactRespository).findAll();
    }

    @Test
    @Disabled
    void getContacts() {
    }

    @Test
    void can_addContact() {
        // Test Data
        Contact cake = new Contact(
                "Cake",
                "+61-412-123-456",
                "cake@gmail.com",
                "cake"
        );

        // Condition
        underTest.addContact(cake);
        // Result
        ArgumentCaptor<Contact> contactArgumentCaptor
                = ArgumentCaptor.forClass(Contact.class);

        // Verify is called with save, capture the Contact that was
        // passed with the save.
        verify(contactRespository)
                .save(contactArgumentCaptor.capture());

        Contact capturedContact = contactArgumentCaptor.getValue();
        assertThat(capturedContact).isEqualTo(cake);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // Test Data
        Contact cake = new Contact(
                "Cake",
                "+61-412-123-456",
                "cake@gmail.com",
                "cake"
        );
        given(contactRespository.selectExistsEmail(cake.getEmail()))
                .willReturn(true);

        assertThatThrownBy(() ->underTest.addContact(cake))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email already exists!");
    }

    @Test
    @Disabled
    void deleteContact() {
    }

    @Test
    @Disabled
    void updateContact() {
    }
}