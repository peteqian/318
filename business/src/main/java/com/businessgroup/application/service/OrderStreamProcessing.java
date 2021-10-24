package com.businessgroup.application.service;

import com.businessgroup.domain.OrderQuantity;
import com.businessgroup.domain.Orders;
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

import java.util.function.Function;

@Configuration
public class OrderStreamProcessing {

    public final static String STATE_STORE = "order-business";

    @Bean
    public Function<KStream<?, Orders>, KStream<String, OrderQuantity>> process(){

        return inputStream -> {
            KTable<String, Long> ordersKTable = inputStream.
                    // Retrieve Orders
                    mapValues(Orders::getProductName).

                    // SQL = Group By Clause
                    groupBy((keyIgnored, value) -> value).

                    count(
                            Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(STATE_STORE).
                                    withKeySerde(Serdes.String()).withValueSerde(Serdes.Long()));

            KStream<String, OrderQuantity> ordersQuantityStream =
                    ordersKTable.toStream().
                            map(((key, value) -> KeyValue.pair(key, new OrderQuantity(key, value))));

            // Code for testing
            ordersQuantityStream.print(Printed.<String, OrderQuantity>toSysOut().withLabel("Console Output"));

            return ordersQuantityStream;
        };
    }
}
