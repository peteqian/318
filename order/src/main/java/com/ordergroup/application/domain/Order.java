package com.ordergroup.application.domain;

import javax.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_Sequence"
    )
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_seqeunce",
            allocationSize = 1
    )
    private Long id;
    private String supplier;
    private String productName;
    private long quantity;

    public Order(){}

    public Order(String supplier, String product, long quantity) {
        this.supplier = supplier;
        this.productName = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", supplier='" + supplier + '\'' +
                ", product='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
