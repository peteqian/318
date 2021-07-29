package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRespository respository){
        return args -> {
            Customer peter = new Customer(
                        "Peter",
                        "peter@gmail.com",
                        LocalDate.of(1995,4,28)
            );

            Customer pineapple = new Customer(
                    "Pineapple",
                    "pineapple@gmail.com",
                    LocalDate.of(1996,4,28)
            );

            respository.saveAll(List.of(peter, pineapple));
        };
    }
}
