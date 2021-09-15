package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.application.domain.Product;
import product.application.domain.ProductDetail;
import product.application.service.ProductDetailService;
import product.application.service.ProductService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/product")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product findProductById(@PathVariable long id){
        return productService.findProductById(id)
                .orElseThrow(RuntimeException::new);
    }
    /*
    @GetMapping("/product/productName?={productName}")
    public Optional<Product> findProductByName(@PathVariable String productName){
        return productService.findProductByName(productName);
    }
    */

    @PostMapping("/product")
    public void createProduct(@RequestBody Product newProduct){
        productService.addProduct(newProduct);
    }

    @PutMapping("/product/{productName}")
    public void updateProduct(
            @PathVariable("productName")String productName,
            @PathVariable(required = false) String productCategory,
            @PathVariable(required = false) double price,
            @PathVariable(required = false) long stockQuantity){
        productService.updateProduct(productName, productCategory, price, stockQuantity);
    }

    @GetMapping("/productDetail")
    public List<ProductDetail> getAllProductDetails(){
        return productDetailService.getAllProductDetails();
    }

    @GetMapping("/productDetail/{productDetailId}")
    public ProductDetail findProductDetailById(@PathVariable long productDetailId){
        return productDetailService.findProductDetailById(productDetailId)
                .orElseThrow(RuntimeException::new);
    }


}
