package com.example.order.Order;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table

public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )

    private Long id;
    private int product_id;
    private int quantity_id;

    public Order(Long id, int _product_id, int _quantity_id){
        this.id = id;
        this.product_id = _product_id;
        this.quantity_id = _quantity_id;
    }

    public Order(int _product_id, int _quantity_id){
        this.product_id = _product_id;
        this.quantity_id = _quantity_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity_id() {
        return quantity_id;
    }

    public void setQuantity_id(int quantity_id) {
        this.quantity_id = quantity_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", quantity_id=" + quantity_id +
                '}';
    }
}
