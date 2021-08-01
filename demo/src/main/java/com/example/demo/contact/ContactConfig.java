package com.example.demo.contact;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ContactConfig {

    @Bean
    CommandLineRunner commandLineRunner(ContactRespository respository){
        return args -> {
            Contact peter = new Contact(
                        "Peter",
                        "+61-412-123-456",
                        "peter@gmail.com",
                        "positionOne",
                        LocalDate.of(1995,4,28)
            );

            Contact pineapple = new Contact(
                    "Pineapple",
                    "+61-412-123-456",
                    "pineapple@gmail.com",
                    "positionTwo",
                    LocalDate.of(1996,4,28)
            );

            respository.saveAll(List.of(peter, pineapple));
        };
    }
}
