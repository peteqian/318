package com.ordergroup.application.domain;

import javax.persistence.*;

@Entity
public class Orders {
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
    private double totalPrice;
    private String cusAddress;
    private String cusPhoneNum;

    public Orders(){}

    public Orders(String supplier, String product, long quantity, double totalPrice, String cusAddress, String cusPhoneNum) {
        this.supplier = supplier;
        this.productName = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.cusAddress = cusAddress;
        this.cusPhoneNum = cusPhoneNum;
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

    public double getTotalPrice() {return  totalPrice;}

    public void setTotalPrice(Double totalPrice) {this.totalPrice = totalPrice;}

    public String getCusAddress() {return cusAddress;}

    public void setCusAddress(String address) {this.cusAddress = address;}

    public String getCusPhoneNum() {return cusPhoneNum;}

    public void setCusPhoneNum(String cusPhoneNum) {this.cusPhoneNum = cusPhoneNum;}

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", supplier='" + supplier + '\'' +
                ", product='" + productName + '\'' +
                ", quantity=" + quantity + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", cusPhoneNum=" + cusPhoneNum + '\'' +
                '}';
    }
}
