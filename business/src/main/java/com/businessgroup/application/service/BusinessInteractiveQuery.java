package com.businessgroup.application.service;

import com.businessgroup.domain.CustomerProduct;
import com.businessgroup.domain.TotalOrderValueCustomer;
import com.businessgroup.exception.CustomerNotFoundException;
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

        String newName = productName.replaceAll("\\+", " ");

        if (keyValueStore().get(newName) != null){
            return keyValueStore().get(newName);
        } else {
            throw new ProductNotFoundException("The product '" + newName +
                    "' does not exist.");
        }
    }

    public List<String> getAllProductsByCustomerID(String customerPhone){
        List<String> productList = new ArrayList<>();
        KeyValueIterator<String, CustomerProduct> all = customerProduct().all();
        int customerCounter = 0;
        while(all.hasNext()){
            CustomerProduct customerProduct = all.next().value;
            String productName = customerProduct.getProduct();
            String custPhone = customerProduct.getPhone();
            if(custPhone.equals(customerPhone)){
                customerCounter = customerCounter + 1;
                productList.add(productName);
            }
        }

        if(customerCounter == 0){
            throw new CustomerNotFoundException("Cannot found the customer with " + customerPhone);
        }

        return productList;
    }

    public double getTotalOrderValueByCustomerID(String customerPhone){

        double totalOrderValue = 0;
        int customerCounter = 0;
        KeyValueIterator<String, TotalOrderValueCustomer> all = totalOrderValue().all();

        while(all.hasNext()){
            TotalOrderValueCustomer customerProduct = all.next().value;
            String custPhone = customerProduct.getPhone();
            if(customerPhone.equals(custPhone)){
                double price = customerProduct.getTotalPrice();
                System.out.println("Retrieving the price from the KTable: " + price);
                totalOrderValue = totalOrderValue + price;
                customerCounter = customerCounter + 1;

            }
        }

        return totalOrderValue;
    }

    public List<CustomerProduct> getCustomerDebugFunction(String customerPhone){

        List<CustomerProduct> customerList = new ArrayList<>();
        KeyValueIterator<String, CustomerProduct> all = customerProduct().all();
        int customerCounter = 0;

        while(all.hasNext()){
            CustomerProduct customer = all.next().value;
            String custPhone = customer.getPhone();
            if(custPhone.equals(customerPhone)){
                customerList.add(customer);
                customerCounter++;
            }
        }

        if(customerCounter == 0){
            throw new CustomerNotFoundException("Cannot found the customer with " + customerPhone);
        }

        return customerList;
    }

    public List<TotalOrderValueCustomer> getTotalOrderDebugFunction(String customerPhone){

        List<TotalOrderValueCustomer> customerList = new ArrayList<>();
        KeyValueIterator<String, TotalOrderValueCustomer> all = totalOrderValue().all();
        int customerCounter = 0;

        while(all.hasNext()){
            TotalOrderValueCustomer customer = all.next().value;
            String custPhone = customer.getPhone();
            if(custPhone.equals(customerPhone)){
                customerList.add(customer);
                customerCounter++;
            }
        }

        if(customerCounter == 0){
            throw new CustomerNotFoundException("Cannot found the customer with " + customerPhone);
        }

        return customerList;
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
