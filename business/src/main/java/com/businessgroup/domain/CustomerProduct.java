package com.businessgroup.domain;

public class CustomerProduct {

    private String product;
    private String phone;
    private String address;
    private double totalPrice;
    private Long quantity;

    public CustomerProduct() {}

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CustomerProduct{" +
                "product='" + product + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", totalPrice=" + totalPrice +
                ", quantity=" + quantity +
                '}';
    }
}
