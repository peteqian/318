package com.customergroup.customer;

import javax.persistence.*;

@Entity
@Table
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )

    private Long id;
    private String companyName;
    private String address;
    private String country;

    private Customer() {}

    public Customer(Long id, String companyName, String address, String country) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.country = country;
    }

    public Customer(String companyName, String address, String country) {
        this.companyName = companyName;
        this.address = address;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", Country='" + country + '\'' +
                '}';
    }
}
