package com.customergroup;

import com.customergroup.domain.Contact;
import com.customergroup.domain.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CustomerApplication {

	private static String PREFIX_CUSTOMER_URL =
			"http://localhost:8080/api/customer";
	private static String PREFIX_CONTACT_URL =
			"http://localhost:8080/api/contact";

	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
		updateCustomer();
	}

	public static int getCustomers(){
		// Returns the total number of customers
		Customer[] customerList =
				restTemplate.getForObject(PREFIX_CUSTOMER_URL, Customer[].class);

		System.out.println("The total length of customer list is: "
				+ customerList.length);

		return customerList.length;
	}

	public static int getContacts(){
		// returns the total number of contacts
		Contact[] contactList =
				restTemplate.getForObject(PREFIX_CONTACT_URL, Contact[].class);
		System.out.println("The total length of contact list is: "
				+ contactList.length);
		return contactList.length;
	}

	public static void updateCustomer(){

		int minLength = Math.min(getCustomers(), getContacts());
		System.out.println("The length of maxLength is " + minLength);

		// Update Customers by attaching Contacts to Customers based on the
		// minimum number of either customer or contacts.
		for(int i = 1; i <= minLength; i++) {
			String putUrl =
					PREFIX_CUSTOMER_URL + "/" + i + "/contact/" + i;
			restTemplate.exchange(putUrl, HttpMethod.PUT,
					null, Class.class);
		}
	}

}
