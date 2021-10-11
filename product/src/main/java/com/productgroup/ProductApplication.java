package com.productgroup;

import com.productgroup.domain.Product;
import com.productgroup.domain.ProductDetail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProductApplication {

	private static String PREFIX_PRODUCT_URL =
			"http://localhost:8081/product";

	private static String PREFIX_PRODUCTDETAIL_URL =
			"http://localhost:8081/productDetail";

	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
		updateProducts();
	}

	public static int getProducts(){
		// Returns the total number of products
		Product[] productList =
				restTemplate.getForObject(PREFIX_PRODUCT_URL, Product[].class);

		System.out.println("The total length of customer list is: "
				+ productList.length);

		return productList.length;
	}

	public static int getProductDetails(){
		// Returns the total number of productDetails
		ProductDetail[] productList =
				restTemplate.getForObject(PREFIX_PRODUCT_URL, ProductDetail[].class);

		System.out.println("The total length of customer list is: "
				+ productList.length);

		return productList.length;
	}

	public static void updateProducts(){
		int minLength = Math.min(getProductDetails(), getProducts());
		System.out.println("The length of maxLength is " + minLength);

		// Update Customers by attaching Contacts to Customers based on the
		// minimum number of either customer or contacts.
		for(int i = 1; i <= minLength; i++) {
			String putUrl =
					PREFIX_PRODUCT_URL + "/" + i + "/productDetail/" + i;
			restTemplate.exchange(putUrl, HttpMethod.PUT,
					null, Class.class);
		}
	}

}
