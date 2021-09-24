package com.ordergroup.application.domain;

import javax.persistence.*;

@Entity
public class OrdersEvent {
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
    private long id;

    private long orderID;
    private String orderString;

    protected OrdersEvent() {}

    public  OrdersEvent(Orders order){
        super();
        this.orderID = orderID;
        this.orderString = order.toString();
    }

    public long getId(){ return id;}

    public long getOrderID(){ return orderID;}

    public void setId(long nId){
        id = nId;
    }

    public void setOrderID(long nOrderId){
        orderID = nOrderId;
    }

}
