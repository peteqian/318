package com.customergroup.infrastructure.config;

import com.customergroup.domain.Contact;
import com.customergroup.domain.Customer;
import com.customergroup.domain.repository.ContactRespository;
import com.customergroup.domain.repository.CustomerRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
    The purpose of the CustomerConfig is to preload information into the
    database for testing purposes.

 */
public class CustomerConfig {

    @Bean
    CommandLineRunner customerLineRunner(CustomerRespository customerRespository,
                                         ContactRespository contactRespository){
        return args -> {
            Customer apple = new Customer(
                    "Apple",
                    "Somewhere in FreedomLand",
                    "U.S.A"
            );

            Customer tesla  = new Customer(
                    "Tesla",
                    "3500 Deer Creek Road " +
                            "Palo Alto, CA 94304",
                    "U.S.A"
            );
            customerRespository.save(apple);
            customerRespository.save(tesla);

            Contact peter = new Contact(
                    "Peter",
                    "+61-412-123-456",
                    "peter@gmail.com",
                    "positionOne"
            );

            Contact pineapple = new Contact(
                    "Pineapple",
                    "+61-412-123-456",
                    "pineapple@gmail.com",
                    "positionTwo"
            );

            contactRespository.save(peter);
            contactRespository.save(pineapple);
        };
    }
}
