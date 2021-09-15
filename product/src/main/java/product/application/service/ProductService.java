package product.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.application.domain.Product;
import product.exception.ProductFailedException;
import product.data.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    // Is this an eventHandler?
    public void addProduct(Product product){
        /*
        Boolean productExist =
                productRepository.findExistingProduct(product.getName());


        if(productExist){
            throw new RuntimeException("Bad");
        }
        */
        productRepository.save(product);
    }

    public void updateProduct(String productName,
                              String productCategory,
                              double price,
                              long stockQuantity){
        /*
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

        if(stockQuantity >= 0){
            product.setStockQuantity(stockQuantity);
        } else {
            throw new ProductFailedException("Stock Quantity cannot be below 0");
        }

         */
    }
}
