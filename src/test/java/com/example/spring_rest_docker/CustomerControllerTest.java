package com.example.spring_rest_docker;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.example.spring_rest_docker.config.AppConfig;
import org.example.spring_rest_docker.models.Customer;
import org.example.spring_rest_docker.controllers.CustomerController;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class CustomerControllerTest {

	@Autowired
	CustomerController custController;

	//Test get list of customer objects
	@Test
	public void testGetCustomers() {
		String expectedResult = "00";
		
		 ResponseEntity<Collection<Customer>> res = custController.getCustomers();
		 String actualResult = res.getHeaders().get("response-code").get(0);
		
		 assertNotNull(res);
		 assertEquals(expectedResult, actualResult);
	}
	
	//Test get single object of customer
	@Test
	public void testGetCustomerById() {
		String expectedResult = "00";
		
		Customer customer = new Customer();
		customer.setName("Customer 1");
		customer.setAddress("Address 1");
		
		custController.addCustomer(customer);
		
		ResponseEntity<Customer> res = custController.getCustomerById((long) 1);
		String actualResult = res.getHeaders().get("response-code").get(0);
		
		assertEquals(expectedResult, actualResult);
	}
	
	//Test add new customer
	@Test
	public void testAddCustomer() {
		String expectedResult = "00";
		
		ResponseEntity<String> res = custController.addCustomer(new Customer((long) 1, "Customer 2", "Address 2"));
		String actualResult = res.getHeaders().get("response-code").get(0);
			
		assertEquals(expectedResult, actualResult);
	}
	
	//Test update existing customer
	@Test
	public void testUpdateCustomer() {
		String expectedResult = "00";
		
		Customer customer = custController.getCustomerById((long) 2).getBody();
		customer.setName("Customer 2 updated");
		
		ResponseEntity<String> res = custController.updateCustomer(customer, (long) 2);
		String actualResult = res.getHeaders().get("response-code").get(0);
		
		assertEquals(expectedResult, actualResult);
	}
	
	//Test delete customer
	@Test
	public void testDeleteCustomer() {
		String expectedResult = "00";
		
		ResponseEntity<String> res = custController.deleteCustomer((long) 1);
		String actualResult = res.getHeaders().get("response-code").get(0);
		
		assertEquals(expectedResult, actualResult);
	}
	
	

}
