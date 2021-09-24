package com.productgroup.application.controller;

import com.productgroup.application.service.ProductInventoryService;
import com.productgroup.application.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.productgroup.domain.Product;
import com.productgroup.domain.ProductDetail;
import com.productgroup.application.service.ProductDetailService;
import com.productgroup.application.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductDetailService productDetailService;
    private final ProductStockService productStockService;
    private final ProductInventoryService productInventory;

    @Autowired
    ProductController(ProductService productService,
                      ProductDetailService productDetailService,
                      ProductStockService productStockService,
                      ProductInventoryService productInventory){
        this.productService = productService;
        this.productDetailService = productDetailService;
        this.productStockService = productStockService;
        this.productInventory = productInventory;
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

    @GetMapping("/product/name={productName}")
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

    @GetMapping("/product/checkInventory/productName={productName}/quantity={quantity}")
    public Map<String, String> checkInventory(@PathVariable("productName") String productName,
                                              @PathVariable("quantity") long quantity) {
        return productInventory.checkInventory(productName, quantity);
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
            @RequestBody ProductDetail productDetail){
        productDetailService.updateProductDetail(productDetailId, productDetail);
    }

    @PutMapping("/product/{productId}/productDetail/{productDetailId}")
    public void assignProductDetails(@PathVariable("productId") long productId,
                                     @PathVariable("productDetailId") long productDetailId){
        productService.insertProductDetail(productId, productDetailId);
    }

    @PutMapping("/product/{productName}/quantity/{quantity}")
    public void updateStock(@PathVariable("productName") String productName,
                                     @PathVariable("quantity") long quantity){
        productStockService.updateStock(productName, quantity);
    }

    /*
    DELETE Mapping
     */

    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @DeleteMapping("/productDetail/{productId}")
    public void deleteProductDetail(@PathVariable("productId") Long productId) {
        productDetailService.deleteProductDetail(productId);
    }

}
