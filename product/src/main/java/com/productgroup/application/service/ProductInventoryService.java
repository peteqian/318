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

    @Transactional
    @Override
    public void updateStock(String productName, long quantity) {
        Product product = productRepository.findProductByName(productName)
                .orElseThrow( ()-> new ProductFailedException("Product " + productName + " cannot be found."));

        // If the inserted quantity results in the product's quantity to be less than
        // zero then throw an error.
        if((product.getStockQuantity() - quantity) < 0) {
            throw new ProductFailedException("There is not enough stock for product: " + productName +
                    ". Quantity you requested: " + quantity +
                    ". Available quantity of the product: " + product.getStockQuantity());
        } else {
            product.setStockQuantity(product.getStockQuantity() - quantity);
        }

    }
}
