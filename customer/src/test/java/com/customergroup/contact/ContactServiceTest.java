package com.customergroup.contact;

import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.ContactNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
    void get_one_contact() {
        // Test Data
        Contact cake = new Contact(
                "Cake",
                "+61-412-123-456",
                "cake@gmail.com",
                "cake"
        );
        underTest.addContact(cake);

        // Condition
        underTest.getContact(cake.getId());
        verify(contactRespository).findById(cake.getId());
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
                .hasMessageContaining("Email " + cake.getEmail() + " already exists!");
    }

    @Test
    void deleteContact() {
        long temp_id = 5;
        Contact tempContact = new Contact(
                temp_id,
                "TemporaryName",
                "+61-412-123-456",
                "temp@gmail.com",
                "tempPosition"
        );
        underTest.addContact(tempContact);
        given(contactRespository.existsById(temp_id))
                .willReturn(true);
        underTest.deleteContact(temp_id);
        verify(contactRespository).deleteById(temp_id);
    }

    @Test
    void will_throw_when_delete_contact_not_found(){
        // Given input
        long temp_id = 5;

        given(contactRespository.existsById(temp_id))
                .willReturn(false);

        // Try
        assertThatThrownBy(() -> underTest.deleteContact(temp_id))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining("Contact with id: " + temp_id + " does not exist!");

        verify(contactRespository, never()).deleteById(any());
    }
}