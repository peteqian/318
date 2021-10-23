package com.ordergroup.domain;

import javax.persistence.*;

@Entity
public class OrdersEvent {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ordersEvent_sequence"
    )
    @SequenceGenerator(
            name = "ordersEvent_sequence",
            sequenceName = "ordersEvent_seqeunce",
            allocationSize = 1
    )
    private long id;

    private long orderID;
    private String orderString;

    protected OrdersEvent() {}

    public  OrdersEvent(Orders order){
        super();
        this.orderID = order.getId();
        this.orderString = order.toString();
    }

    public long getId(){
        return id;
    }

    public long getOrderID(){
        return orderID;
    }

    public void setId(long nId){
        id = nId;
    }

    public void setOrderID(long nOrderId){
        orderID = nOrderId;
    }

    @Override
    public String toString() {
        return "OrdersEvent{" +
                "id=" + id +
                ", orderID=" + orderID +
                ", orderString='" + orderString + '\'' +
                '}';
    }
}
