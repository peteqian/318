package com.productgroup.application.service;

import com.productgroup.domain.ProductDetail;
import com.productgroup.exception.ProductNotFoundException;
import com.productgroup.infrastructure.repository.ProductDetailRepository;
import com.productgroup.exception.ProductFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductDetailService(ProductDetailRepository productDetailRepository){
        this.productDetailRepository = productDetailRepository;
    }

    public List<ProductDetail> getAllProductDetails(){
        return productDetailRepository.findAll();
    }

    public ProductDetail findProductDetailById(long id){
        return productDetailRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cannot find the product detail" +
                        "with the id " + id));
    }

    public void addProductDetail(ProductDetail productDetail){
        productDetailRepository.save(productDetail);
    }

    public void deleteProductDetail(Long productId) {
        boolean exists = productDetailRepository.existsById(productId);
        if(!exists){
            throw new ProductNotFoundException("Product with id " + productId + " does not exist!");
        }
        productDetailRepository.deleteById(productId);
    }

    public void updateProductDetail(long id, String description, String comment){
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow( ()-> new ProductFailedException("Cannot find the product description of id: " + id));

        if (description != null && description.length() > 0){
            productDetail.setDescription(description);
        }

        if (comment != null && comment.length() > 0){
            productDetail.setComment(comment);
        }
    }

}
