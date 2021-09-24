package com.productgroup.application.service;

import com.productgroup.domain.ProductDetail;
import com.productgroup.exception.ProductNotFoundException;
import com.productgroup.infrastructure.repository.ProductDetailRepository;
import com.productgroup.exception.ProductFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void updateProductDetail(long id, ProductDetail productDetail){
        ProductDetail reposProductDetail = productDetailRepository.findById(id)
                .orElseThrow( ()-> new ProductFailedException("Cannot find the product description of id: " + id));

        if (productDetail.getDescription() != null && productDetail.getDescription().length() > 0){
            reposProductDetail.setDescription(productDetail.getDescription());
        }

        if (productDetail.getComment() != null && productDetail.getComment().length() > 0){
            reposProductDetail.setComment(productDetail.getComment());
        }
    }

}
