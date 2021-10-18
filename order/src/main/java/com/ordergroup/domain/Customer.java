package com.ordergroup.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )

    // Looks for address property within nested property of JSON object
    @JsonProperty("address")
    private String address;
    private String phone;

    protected Customer(){}

    public Customer(String address, String phone){
        this.address = address;
        this.phone = phone;
    }

    public String getAddress(){return address;}

    public void setAddress(String address){this.address = address;}

    public String getPhone(){return phone;}

    public void setPhone(String phone){this.phone = phone;}
}
