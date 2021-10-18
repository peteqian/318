package com.ordergroup;

import com.ordergroup.domain.OrdersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderApplication {

	private static final Logger log =
			LoggerFactory.getLogger(OrderApplication.class);

	private static final String PREFIX_ORDER_URL = "";

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
	/*
	TO DO:
	Need to be able to:
	1. Generate an order every 2 seconds.
		1.1 First api request to product - get all products
		1.2 Second api request to customer - get all customers
		1.3 Convert productNames into an array of String
		1.4 Convert customer into an array to get length
		1.5 Length of customer will be used as a supplement for custID
		1.6 Random no gen for customer, product and quantity.
		1.7 Convert into raw body in JSON format.
	2. Send the OrdersEvent to Kafka

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate,
								 StreamBridge streamBridge) throws Exception{
		return args -> {
			try{
				OrdersEvent ordersEvent =
						restTemplate.getForObject(PREFIX_ORDER_URL,
								OrdersEvent.class);
				assert ordersEvent != null;
				log.info(ordersEvent.toString());

				streamBridge.send("appliance-outboard", ordersEvent);

				Thread.sleep(2000);
			} catch(InterruptedException ignored){

			}
		};
	}
	*/

}
