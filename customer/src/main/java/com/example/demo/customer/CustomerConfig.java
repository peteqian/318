package com.example.demo.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner customerLineRunner(CustomerRespository respository){
        return args -> {
            Customer apple = new Customer(
                    "Apple",
                    "Somewhere in FreedomLand",
                    "FreedomLand"
            );
            respository.saveAll(List.of(apple));
        };
    }
}
