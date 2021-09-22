package com.productgroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.productgroup.domain.Product;
import com.productgroup.domain.ProductDetail;
import com.productgroup.application.service.ProductDetailService;
import com.productgroup.application.service.ProductService;

import java.util.List;

@RestController

public class ProductController {
    private final ProductService productService;
    private final ProductDetailService productDetailService;

    @Autowired
    ProductController(ProductService productService,
                      ProductDetailService productDetailService){
        this.productService = productService;
        this.productDetailService = productDetailService;
    }

    /*
    GET MAPPING
     */

    @GetMapping("/product")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product findProductById(@PathVariable long id){
        return productService.findProductById(id);
    }

    @GetMapping("/product/name?={productName}")
    public Product findProductByName(@PathVariable String productName){
        return productService.findProductByName(productName);
    }

    @GetMapping("/productDetail")
    public List<ProductDetail> getAllProductDetails(){
        return productDetailService.getAllProductDetails();
    }

    @GetMapping("/productDetail/{productDetailId}")
    public ProductDetail findProductDetailById(@PathVariable long productDetailId){
        return productDetailService.findProductDetailById(productDetailId);
    }

    /*
    POST MAPPING
     */

    @PostMapping("/product")
    public void createProduct(@RequestBody Product newProduct){
        productService.addProduct(newProduct);
    }

    @PostMapping("/productDetail")
    public void createProductDetail(@RequestBody ProductDetail newProductDetail){
        productDetailService.addProductDetail(newProductDetail);
    }

    /*
    PUT MAPPING
     */

    @PutMapping("/product/{productName}")
    public void updateProduct(
            @PathVariable("productName")String productName,
            @PathVariable(required = false) String productCategory,
            @PathVariable(required = false) double price,
            @PathVariable(required = false) long stockQuantity){
        productService.updateProduct(productName, productCategory, price, stockQuantity);
    }

    @PutMapping("/productDetail/{productDetailId}")
    public void updateProductDetail(
            @PathVariable("productDetailId")long productDetailId,
            @PathVariable(required = false) String description,
            @PathVariable(required = false) String comment){
        productDetailService.updateProductDetail(productDetailId, description, comment);
    }

    @PutMapping("/product/{productId}/productDetail/{productDetailId}")
    public void assignProductDetails(@PathVariable("productId") long productId,
                                     @PathVariable("productDetailId") long productDetailId){
        productService.insertProductDetail(productId, productDetailId);
    }

    /*
    DELETE Mapping
     */

}
