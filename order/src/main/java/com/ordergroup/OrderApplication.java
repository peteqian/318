package com.ordergroup;

import com.ordergroup.domain.OrdersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}
