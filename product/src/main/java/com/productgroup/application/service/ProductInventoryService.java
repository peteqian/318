package com.productgroup.application.service;

import com.productgroup.domain.IProductInventory;
import com.productgroup.domain.Product;
import com.productgroup.exception.ProductFailedException;
import com.productgroup.infrastructure.repository.ProductDetailRepository;
import com.productgroup.infrastructure.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void updateStock(String productName, long quantity) {
        Product product = productRepository.findProductByName(productName)
                .orElseThrow( ()-> new ProductFailedException("Product " + productName + " cannot be found."));

        product.setStockQuantity(product.getStockQuantity() - quantity);
    }
}
