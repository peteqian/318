package com.example.demo.customer;

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
public class CustomerServiceTest {

    @Mock
    private CustomerRespository customerRespository;
    private CustomerService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CustomerService(customerRespository);
    }

    @Test
    void getAllCustomers(){
        underTest.getCustomers();
        verify(customerRespository).findAll();
    }

    @Test
    void can_add_customer(){
        Customer customerOne = new Customer(
            "companyName",
            "address",
            "country"
        );

        underTest.addCustomer(customerOne);
        ArgumentCaptor<Customer> customerArgumentCaptor
                = ArgumentCaptor.forClass(Customer.class);

        verify(customerRespository)
                .save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer).isEqualTo(customerOne);
    }

    @Test
    void will_throw_when_company_already_exists(){
        Customer apple = new Customer(
                "Apple",
                "Somewhere in FreedomLand",
                "FreedomLand"
        );

        given(customerRespository.selectExistingCompany(apple.getCompanyName()))
                .willReturn(true);
        assertThatThrownBy(()-> underTest.addCustomer(apple))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Company Name: " + apple.getCompanyName() + " already exists!");
    }
}
