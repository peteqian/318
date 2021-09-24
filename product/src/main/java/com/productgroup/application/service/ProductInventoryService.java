package com.productgroup.application.service;

import com.productgroup.domain.IProductInventory;
import com.productgroup.domain.Product;
import com.productgroup.exception.ProductFailedException;
import com.productgroup.infrastructure.repository.ProductDetailRepository;
import com.productgroup.infrastructure.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductInventoryService implements IProductInventory {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductInventoryService(ProductRepository productRepository,
                                   ProductDetailRepository productDetailRepository){
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    public double checkInventory(String productName, long quantity) {
        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new ProductFailedException("Product " + productName + " cannot be found."));

        System.out.print(product.getProductName() + " " + product.getStockQuantity());

        if (product.getStockQuantity() >= quantity) {
            return product.getPrice();
        } else {
            throw new ProductFailedException("There is not enough stock for product: " + productName);
        }
    }
}
