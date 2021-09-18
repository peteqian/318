package com.productgroup.application.service;

import com.productgroup.application.domain.Product;
import com.productgroup.application.domain.ProductDetail;
import com.productgroup.data.ProductDetailRepository;
import com.productgroup.data.ProductRepository;
import com.productgroup.exception.ProductFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductDetailRepository productDetailRepository){
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cannot find a product with " +
                        "the id: " + id));
    }

    public Product findProductByName(String productName){
        return productRepository.findProductByName(productName)
                .orElseThrow(()-> new RuntimeException("Cannot find a product with " +
                        "the name: " + productName));
    }

    public void addProduct(Product product){

        Boolean productExist =
                productRepository.findExistingProduct(product.getProductName());
        if(productExist){
            throw new ProductFailedException("A product with this name already exists!");
        }
        productRepository.save(product);
    }

    public void updateProduct(String productName,
                              String productCategory,
                              double price,
                              long stockQuantity){
        Product product = productRepository.findProductByName(productName)
                .orElseThrow( ()-> new ProductFailedException("Product " + productName + " cannot be found."));

        if (productCategory != null && productCategory.length() > 0){
            product.setProductCategory(productCategory);
        }

        if(price >= 0.0){
            product.setPrice(price);
        } else {
            throw new ProductFailedException("Price cannot be below 0.0");
        }

        if(stockQuantity >= 0 && (Long)stockQuantity != null){
            product.setStockQuantity(stockQuantity);
        } else {
            throw new ProductFailedException("Stock Quantity cannot be below 0");
        }
    }

    public void insertProductDetail(long productId, long productDetailId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Cannot find a product with the id: " + productId));

        ProductDetail productDetail = productDetailRepository.findById(productDetailId)
                .orElseThrow( ()->
                        new ProductFailedException("Cannot find the product description of id: "
                                + productDetailId));
        
    }

}
