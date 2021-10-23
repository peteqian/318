package com.businessgroup.application.service;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInteractiveQuery {

    private InteractiveQueryService interactiveQueryService;

    public OrderInteractiveQuery(InteractiveQueryService interactiveQueryService){
        this.interactiveQueryService = interactiveQueryService;
    }

    public List<String> getAllProductList(){
        List<String> productList = new ArrayList<>();
        KeyValueIterator<String, Long> all = keyValueStore().all();
        while(all.hasNext()){
            String next = all.next().key;
            productList.add(next);
        }
        return productList;
    }

    private ReadOnlyKeyValueStore<String, Long> keyValueStore(){
        return this.interactiveQueryService.getQueryableStore(
                OrderStreamProcessing.STATE_STORE,
                QueryableStoreTypes.keyValueStore()
        );
    }

}
