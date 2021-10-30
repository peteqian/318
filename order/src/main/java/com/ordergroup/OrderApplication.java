package com.ordergroup;


import com.ordergroup.domain.Customer;
import com.ordergroup.domain.Orders;
import com.ordergroup.domain.OrdersEvent;
import com.ordergroup.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class OrderApplication {

	private static final Logger log =
			LoggerFactory.getLogger(OrderApplication.class);

	private static final String PREFIX_ORDER_URL =
			"http://localhost:8082/api/orders";

	private static final String PREFIX_CUSTOMER_URL =
			"http://localhost:8080/api/customer";

	private static final String PREFIX_PRODUCT_URL =
			"http://localhost:8081/product";

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	public static Customer[] getCustomerList(RestTemplate restTemplate){

		Customer[] customerList =
				restTemplate.getForObject(PREFIX_CUSTOMER_URL, Customer[].class);
		return customerList;
	}

	public static Product[] getProductList(RestTemplate restTemplate){
		Product[] productList =
				restTemplate.getForObject(PREFIX_PRODUCT_URL, Product[].class);
		return productList;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate,
								 StreamBridge streamBridge) throws Exception{
		/*
		TO DO:
		Need to be able to:
		1. Generate an order every 2 seconds.
		1.1 First api request to product - get all products
		1.2 Second api request to customer - get all customers
		1.3 Convert productNames into an array
		1.4 Convert customer into an array
		1.5 Length of customer will be used as a supplement for custID
		1.6 Random no gen for customer, product and quantity.
		1.7 Convert into raw body in JSON format.
		2. Send the OrdersEvent to Kafka
		*/
		Customer[] customerList = getCustomerList(restTemplate);
		Product[] productList = getProductList(restTemplate);
		AtomicInteger counter = new AtomicInteger(1);

		System.out.println("The length of the customer array is: "
				+ customerList.length);

		System.out.println("The length of the product array is: "
				+ productList.length);

		return args -> {

			try{
				while(!Thread.currentThread().isInterrupted()){
					try{
						// Create variables for randomising numbers
						Long randCustomerId =
								ThreadLocalRandom.current().nextLong(customerList.length) + 1;
						Integer randProductId =
								ThreadLocalRandom.current().nextInt(productList.length);
						Long randQuantity =
								ThreadLocalRandom.current().nextLong(1, 10);


						// Create HTTP Header
						MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
						headers.add("Content-Type", "application/json");

						// Create raw body
						HashMap<String, String> bodyParam = new HashMap<>();
						bodyParam.put("custID", randCustomerId.toString());
						bodyParam.put("productName",
								productList[randProductId].getProductName());
						bodyParam.put("quantity", randQuantity.toString());

						// Create HTTP Enttity
						HttpEntity entity = new HttpEntity(bodyParam, headers);

						// HTTP Response Entity
						ResponseEntity<Class> result = restTemplate.exchange(PREFIX_ORDER_URL,
								HttpMethod.POST,entity, Class.class);
						log.info(result.toString());
					} catch (Exception e){
						// Log the bad request
						log.info(e.toString());
					}

					// Get the recently made order
					String url = PREFIX_ORDER_URL + "/" + counter;

					// Place Order into an object
					Orders ordersEvent = restTemplate.getForObject(url,
							Orders.class);

					assert ordersEvent != null;

					log.info(ordersEvent.toString());

					streamBridge.send("order-outbound", ordersEvent);
					counter.getAndIncrement();

					// Set to 10s just because I didn't want to spam server
					Thread.sleep(2000);
				}

			} catch(InterruptedException ignored){}

		};

	}

}
