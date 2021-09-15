package product.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import product.application.domain.Product;
import product.application.domain.ProductDetail;
import product.data.ProductDetailRepository;
import product.data.ProductRepository;

import java.util.List;

@Configuration
public class productApplicationConfig {
    @Bean
    CommandLineRunner productLineRunner (ProductRepository productRepository,
                                         ProductDetailRepository productDetailRepository){
        return args -> {
            Product productOne = new Product(
                    "Beverage",
                    "Water",
                    1.05,
                    10
            );
            Product productTwo = new Product(
                    "Food",
                    "Hotdog",
                    10.00,
                    5
            );

            productRepository.saveAll(List.of(productOne, productTwo));

            ProductDetail productDetailOne = new ProductDetail(
                    "It is a fluid",
                    "Everyone needs it in their lives"
            );

            ProductDetail productDetailTwo = new ProductDetail(
                    "Large 15 pepperoni",
                    "Not from dominos."
            );

            productDetailRepository.saveAll(List.of(productDetailOne,
                                                    productDetailTwo));
        };
    }
}
