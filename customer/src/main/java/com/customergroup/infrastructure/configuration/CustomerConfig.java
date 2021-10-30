package com.customergroup.infrastructure.configuration;

import com.customergroup.domain.Contact;
import com.customergroup.domain.Customer;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.infrastructure.repository.CustomerRespository;
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
                    "CompanyApple",
                    "Somewhere in FreedomLand",
                    "U.S.A");

            Customer bravo = new Customer("CompanyBravo",
                    "88 Pitt St, Sydney, NSW, 2000",
                    "Australia");

            Customer charlie = new Customer("CompanyCharlie",
                    "Bit my finger St, Sydney, NSW 2000",
                    "Australia");

            Customer delta = new Customer("CompanyDelta",
                    " 71 Bank St, North Sydney, NSW 2060",
                    "Australia");

            Customer echo = new Customer("CompanyEcho",
                    "84 Pitt St, Sydney, NSW 2000",
                    "Australia");

            Customer epic = new Customer("Epic Games",
                    "620 Crossroads Blvd., Cary, NC",
                    "USA");

            Customer tesla  = new Customer("Tesla",
                    "3500 Deer Creek Road Palo Alto, CA 94304",
                    "USA");

            Customer valve = new Customer("Valve",
                    "10400 Northeast Fourth Street Floor 14, Bellevue, WA 98004 USA",
                    "USA");

            Customer microsoft = new Customer("Microsoft",
                    "Level 24-30, 1 Denison Street, North Sydney, NSW, 2060",
                    "USA");

            Customer google = new Customer("Google",
                    "Wharf 7, 58 Pirrama Rd, Pyrmont NSW 2009",
                    "Australia");

            Contact gabe = new Contact("Gabe Newell",
                    "+61-111-111-111",
                    "valve@gmail.com",
                    "CEO");

            Contact peter = new Contact("Peter",
                    "+61-111-111-112",
                    "peter@gmail.com",
                    "positionOne");

            Contact pineapple = new Contact("Pineapple",
                    "+61-111-111-113",
                    "pineapple@gmail.com",
                    "positionTwo");

            Contact timSweeney = new Contact("Tim Sweeney",
                    "+61-111-111-114",
                    "epic@apple.killer.com",
                    "CEO");

            Contact johnMcAfee = new Contact("John McAfee",
                    "+61-111-111-115",
                    "john.mcafee@mcafeesolutions.com",
                    "CEO");

            Contact elonMusk = new Contact("Elon Musk",
                    "+61-111-111-116",
                    "elon.musk@tesla.com",
                    "CEO");

            Contact scottHerford = new Contact("Scott Herford",
                    "+61-111-111-117",
                    "herford@yahoo.com.au",
                    "Documentation");

            Contact victorYing = new Contact("Victor Ying",
                    "+61-111-111-118",
                    "ying@yahoo.com.au",
                    "Documentation");

            Contact larryPage = new Contact("Larry Page",
                    "+61-111-111-119",
                    "google@google.com",
                    "Founder");

            Contact billGates = new Contact("Bill Gates",
                    "+61-111-111-120",
                    "gates@microsoft.com",
                    "Founder");

            customerRespository.save(apple);
            customerRespository.save(bravo);
            customerRespository.save(charlie);
            customerRespository.save(delta);
            customerRespository.save(echo);
            customerRespository.save(epic);
            customerRespository.save(tesla);
            customerRespository.save(valve);
            customerRespository.save(google);
            customerRespository.save(microsoft);

            contactRespository.save(peter);
            contactRespository.save(scottHerford);
            contactRespository.save(victorYing);
            contactRespository.save(pineapple);
            contactRespository.save(johnMcAfee);
            contactRespository.save(timSweeney);
            contactRespository.save(elonMusk);
            contactRespository.save(gabe);
            contactRespository.save(larryPage);
            contactRespository.save(billGates);

        };
    }
}
