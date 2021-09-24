package com.ordergroup.domain;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    private String productName;
    private String price;
    private String supplier;

    protected Product(){}

    public Product(String price, String supplier){
        this.price = price;
        this.supplier = supplier;
    }

    public String getPrice(){return price;}

    public void setPrice(String price){this.price = price;}

    public String getSupplier(){return supplier;}

    public void setSupplier(String supplier){this.supplier = supplier;}

    public String getProductName(){return productName;}

    public void setProductName(String productName) {this.productName=productName;}
}
