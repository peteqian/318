package com.businessgroup.application.service;

import com.businessgroup.domain.CustomerProduct;
import com.businessgroup.domain.TotalOrderValueCustomer;
import com.businessgroup.exception.ProductNotFoundException;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessInteractiveQuery {

    private InteractiveQueryService interactiveQueryService;

    public BusinessInteractiveQuery(InteractiveQueryService interactiveQueryService){
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

    public double getTotalOrderValueByCustomerID(String customerPhone){
        double totalOrderValue = 0;
        KeyValueIterator<String, TotalOrderValueCustomer> all = totalOrderValue().all();
        while(all.hasNext()){
            TotalOrderValueCustomer customerProduct = all.next().value;
            String custPhone = customerProduct.getPhone();
            double price = customerProduct.getTotalPrice();
            if(custPhone.equals(customerPhone)){
                totalOrderValue += price;
            }
        }
        return totalOrderValue;
    }

    private ReadOnlyKeyValueStore<String, Long> keyValueStore(){
        return this.interactiveQueryService.getQueryableStore(
                BusinessStreamProcessing.STATE_STORE,
                QueryableStoreTypes.keyValueStore()
        );
    }

    private ReadOnlyKeyValueStore<String, CustomerProduct> customerProduct(){
        return this.interactiveQueryService.getQueryableStore(
                BusinessStreamProcessing.CUSTOMER_STORE,
                QueryableStoreTypes.keyValueStore()
        );
    }

    private ReadOnlyKeyValueStore<String, TotalOrderValueCustomer> totalOrderValue(){
        return this.interactiveQueryService.getQueryableStore(
                BusinessStreamProcessing.TOTAL_VALUE_STORE,
                QueryableStoreTypes.keyValueStore()
        );
    }

}
