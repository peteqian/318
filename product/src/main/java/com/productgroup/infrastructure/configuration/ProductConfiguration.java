package com.productgroup.infrastructure.configuration;

import com.productgroup.infrastructure.repository.ProductDetailRepository;
import com.productgroup.infrastructure.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.productgroup.domain.Product;
import com.productgroup.domain.ProductDetail;

@Configuration
public class ProductConfiguration {
    @Bean
    CommandLineRunner productLineRunner (ProductRepository productRepository,
                                         ProductDetailRepository productDetailRepository){
        return args -> {
            Product productOne = new Product(
                    "Beverage",
                    "Water",
                    1.05,
                    10,
                    "Coca-Cola"
            );
            Product productTwo = new Product(
                    "Food",
                    "Hotdog",
                    10.00,
                    5,
                    "Random-street-vendor"
            );
            Product productThree = new Product(
                    "Hardware",
                    "Nvidia RTX 3080",
                    2199.99,
                    1000,
                    "PLE Computers"
            );

            Product productFour = new Product(
                    "Hardware",
                    "AMD Ryzen R5 5600X",
                    469.00,
                    1000,
                    "PC Case Gear"
            );

            Product productFive = new Product(
                    "Software",
                    "Windows 2000 - Home",
                    1.00,
                    10000000,
                    "Microsoft"
            );


            productRepository.save(productOne);
            productRepository.save(productTwo);
            productRepository.save(productThree);
            productRepository.save(productFour);
            productRepository.save(productFive);

            ProductDetail productDetailOne = new ProductDetail(
                    "It is a fluid",
                    "Everyone needs it in their lives"
            );

            ProductDetail productDetailTwo = new ProductDetail(
                    "Large 15 pepperoni",
                    "Not from dominos."
            );

            ProductDetail productDetailThree = new ProductDetail(
                    "Is very hard to get.",
                    "Usually need to find on black market."
            );

            ProductDetail productDetailFour = new ProductDetail(
                    "A very rare silicon material.",
                    "Usually need to find on black market."
            );

            ProductDetail productDetailFive = new ProductDetail(
                    "A Microsoft product that contains the leading-edge " +
                            "technology.",
                    "Usually need to find on black market."
            );

            productDetailRepository.save(productDetailOne);
            productDetailRepository.save(productDetailTwo);
            productDetailRepository.save(productDetailThree);
            productDetailRepository.save(productDetailFour);
            productDetailRepository.save(productDetailFive);

        };
    }
}
