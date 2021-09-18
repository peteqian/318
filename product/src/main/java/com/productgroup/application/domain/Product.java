package com.productgroup.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.productgroup.exception.ProductFailedException;

import javax.persistence.*;

@Entity
@Table
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private String productCategory;
    private String productName;
    private double price;
    private long stockQuantity;

    // Build a one-to-onne relationship between Product and ProductDetail.

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productDetail_id")
    @JsonIgnore

    // @OneToOne(mappedBy = "product")
    private ProductDetail productDetail = null;

    public Product(){}

    public Product(Long id, String productCategory, String productName, double price, long stockQuantity) {
        this.id = id;
        this.productCategory = productCategory;
        this.productName = productName;
        /*
            Value Objects
            Self-validating -> Invariant check
         */
        if(price < 0.0){throw new ProductFailedException("You cannot enter a product with a negative price.");}
        this.price = price;
        if(stockQuantity < 0){throw new ProductFailedException("You cannot enter a product with negative quantity");}
        this.stockQuantity = stockQuantity;
    }

    public Product(String productCategory, String productName, double price, long stockQuantity) {
        this.productCategory = productCategory;
        this.productName = productName;
        /*
            Value Objects
            Self-validating -> Invariant check
         */
        if(price < 0.0){throw new ProductFailedException("You cannot enter a product with a negative price.");}
        this.price = price;
        if(stockQuantity < 0){throw new ProductFailedException("You cannot enter a product with negative quantity");}
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCategory='" + productCategory + '\'' +
                ", name='" + productName + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", productDetail=" + productDetail +
                '}';
    }
}
