package com.customergroup.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner customerLineRunner(CustomerRespository respository,
                                         ContactRespository contactRespository){
        return args -> {
            Customer apple = new Customer(
                    "Apple",
                    "Somewhere in FreedomLand",
                    "FreedomLand"
            );
            respository.saveAll(List.of(apple));

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

            contactRespository.saveAll(List.of(peter, pineapple));
        };
    }
}
