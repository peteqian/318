package com.businessgroup.application.service;

import com.businessgroup.domain.CustomerProduct;
import com.businessgroup.exception.ProductNotFoundException;
import org.apache.kafka.streams.KeyValue;
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

    public long getProductQuantity(String productName){
        if (keyValueStore().get(productName) != null){
            return keyValueStore().get(productName);
        } else {
            throw new ProductNotFoundException("The product '" + productName +
                    "' does not exist.");
        }
    }

    public List<String> getAllProductsByCustomerID(String customerPhone){
        List<String> productList = new ArrayList<>();
        KeyValueIterator<String, CustomerProduct> all = customerProduct().all();
        while(all.hasNext()){
            CustomerProduct customerProduct = all.next().value;
            String productName = customerProduct.getProduct();
            String custPhone = customerProduct.getPhone();
            if(custPhone.equals(customerPhone)){
                productList.add(productName);
            }
        }
        return productList;
    }

    private ReadOnlyKeyValueStore<String, Long> keyValueStore(){
        return this.interactiveQueryService.getQueryableStore(
                OrderStreamProcessing.STATE_STORE,
                QueryableStoreTypes.keyValueStore()
        );
    }

    private ReadOnlyKeyValueStore<String, CustomerProduct> customerProduct(){
        return this.interactiveQueryService.getQueryableStore(
                OrderStreamProcessing.CUSTOMER_STORE,
                QueryableStoreTypes.keyValueStore()
        );
    }

}
