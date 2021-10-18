package com.ordergroup;


import com.ordergroup.domain.Customer;
import com.ordergroup.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

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
		return args -> {
			Customer[] customerList = getCustomerList(restTemplate);
			Product[] productList = getProductList(restTemplate);

			System.out.println("The length of the customer array is: "
					+ customerList.length);

			System.out.println("The length of the product array is: "
					+ productList.length);

			// Create random variables
			Long randCustomerId = ThreadLocalRandom.current().nextLong(customerList.length);
			int randProductId = (int) Math.random() * productList.length+1;
			Long randQuantity = ThreadLocalRandom.current().nextLong(10);

			String rawbody =
					"{"
					+"\"custID\": \"" + randCustomerId+ "\","
					+ "\"productName\": \"" + productList[randProductId].getProductName() + "\","
					+ "\"quantity\": \"" + 1 + "\""
					+ "}";

			System.out.println("Body is: " + rawbody);

			// Create HTTP Header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// Create HTTP Enttity
			HttpEntity<String> entity = new HttpEntity<String>(rawbody,
					headers);

			System.out.println("Sending to URL");
			URI values = restTemplate.postForLocation(PREFIX_ORDER_URL, entity);
			System.out.println("Path being sent to: " + values.getPath());

			/*
			try{
				while(!Thread.currentThread().isInterrupted()){
					OrdersEvent ordersEvent =
							restTemplate.getForObject(PREFIX_ORDER_URL,
									OrdersEvent.class);
					assert ordersEvent != null;
					log.info(ordersEvent.toString());

					streamBridge.send("appliance-outboard", ordersEvent);
					Thread.sleep(2000);
				}
			} catch(InterruptedException ignored){}
			*/
		};

	}

}
