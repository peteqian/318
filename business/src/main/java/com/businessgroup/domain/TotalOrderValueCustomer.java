package com.businessgroup.domain;

public class TotalOrderValueCustomer {

    private Long orderId;
    private String phone;
    private String address;
    private double totalPrice;

    public Long getId() {
        return orderId;
    }

    public void setId(Long id) {
        this.orderId= id;
    }

    public TotalOrderValueCustomer(){}

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

    @Override
    public String toString() {
        return "TotalOrderValueCustomer{" +
                "orderId =" + orderId +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
