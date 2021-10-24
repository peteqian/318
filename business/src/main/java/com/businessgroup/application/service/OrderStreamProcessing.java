package com.businessgroup.application.service;

import com.businessgroup.domain.CustomerProduct;
import com.businessgroup.domain.OrderQuantity;
import com.businessgroup.domain.Orders;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class OrderStreamProcessing {

    public final static String STATE_STORE = "order-business";
    public final static String CUSTOMER_STORE = "customer-product";

    @Bean
    public Function<KStream<?, Orders>, KStream<String, OrderQuantity>> process(){

        return inputStream -> {

            inputStream.map((k, v) -> {
                String productName = v.getProductName();
                String custPhoneNum = v.getCusPhoneNum();
                String custAddress = v.getCusAddress();

                CustomerProduct customerProductObj = new CustomerProduct();
                customerProductObj.setProduct(productName);
                customerProductObj.setPhone(custPhoneNum);
                customerProductObj.setAddress(custAddress);

                String new_key = custPhoneNum + custAddress + productName;
                return KeyValue.pair(new_key, customerProductObj);
            }).toTable(
                    Materialized.<String, CustomerProduct, KeyValueStore<Bytes, byte[]>>
                                    as(CUSTOMER_STORE).
                            withKeySerde(Serdes.String()).
                            // a custom value serde for this state store
                                    withValueSerde(customerProductSerde())
            );

            KTable<String, Long> ordersKTable = inputStream.
                    mapValues(Orders::getProductName).
                    groupBy((keyIgnored, value) -> value).
                    count(
                            Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(STATE_STORE).
                                    withKeySerde(Serdes.String()).withValueSerde(Serdes.Long())
                    );

            KStream<String, OrderQuantity> ordersQuantityStream =
                    ordersKTable.toStream().
                            map(((key, value) -> KeyValue.pair(key, new OrderQuantity(key, value))));

            ordersQuantityStream.print(Printed.<String, OrderQuantity>toSysOut().withLabel("Console Output"));

            return ordersQuantityStream;
        };
    }

    public Serde<CustomerProduct> customerProductSerde(){
        JsonSerde<CustomerProduct> customerProductJsonSerde = new JsonSerde<>();

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.businessgroup.domain.CustomerProduct");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        customerProductJsonSerde.configure(configProps, false);

        return customerProductJsonSerde;
    }

}
