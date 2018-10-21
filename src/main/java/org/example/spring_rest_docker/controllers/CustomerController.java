package org.example.spring_rest_docker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.example.spring_rest_docker.models.Customer;
import org.example.spring_rest_docker.services.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

	@Autowired
	CustomerService custService;

	//Define success header KV
	private static Map<String, String> successHeaderKV;
	static {
		successHeaderKV = new HashMap<>();
		successHeaderKV.put("responseCode", "00");
		successHeaderKV.put("responseDesc", "success");
		successHeaderKV.put("contentType", "application/json");
	}

	//Define data not found KV
	private static Map<String, String> dataNotFoundKV;
	static {
		dataNotFoundKV = new HashMap<>();
		dataNotFoundKV.put("responseCode", "01"); 
		dataNotFoundKV.put("responseDesc", "data not found");
		dataNotFoundKV.put("contentType", "application/json");
	}
	
	//Define data already exists KV
	private static Map<String, String> dataAlreadyExistsKV;
	static {
		dataAlreadyExistsKV = new HashMap<>();
		dataAlreadyExistsKV.put("responseCode", "02"); //Define 02 as data already exists
		dataAlreadyExistsKV.put("responseDesc", "data already exists");
		dataAlreadyExistsKV.put("contentType", "application/json");
		
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Customer>> getCustomers() {
		HttpHeaders headers = new HttpHeaders();
		Collection<Customer> customers = custService.getCustomers();
		headers.add("response-code", successHeaderKV.get("responseCode"));
		headers.add("response-desc", successHeaderKV.get("responseDesc"));
		headers.add(HttpHeaders.CONTENT_TYPE, successHeaderKV.get("contentType"));
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(customers);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		HttpHeaders headers = new HttpHeaders();
		Customer customer = custService.getCustomerById(id);
		if (customer != null) {
			headers.add("response-code", successHeaderKV.get("responseCode"));
			headers.add("response-desc", successHeaderKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, successHeaderKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(customer);
		} else {
			headers.add("response-code", dataNotFoundKV.get("responseCode"));
			headers.add("response-desc", dataNotFoundKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, dataNotFoundKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(customer);
		}
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
		HttpHeaders headers = new HttpHeaders();
		String resp = custService.addCustomer(customer.getName(), customer.getAddress());
		if(resp.equals("success")) {
			headers.add("response-code", successHeaderKV.get("responseCode"));
			headers.add("response-desc", successHeaderKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, successHeaderKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("{\"Message\":\"Customer inserted successfully\"}");
		}else {
			headers.add("response-code", dataAlreadyExistsKV.get("responseCode"));
			headers.add("response-desc", dataAlreadyExistsKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, dataAlreadyExistsKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body("{\"Message\":\"Customer name is already exists\"}");
		}
	}
	
	@PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable Long id){
		HttpHeaders headers = new HttpHeaders();
		String resp = custService.updateCustomer(id, customer.getName(), customer.getAddress());
		if (resp.equals("success")) {
			headers.add("response-code", successHeaderKV.get("responseCode"));
			headers.add("response-desc", successHeaderKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, successHeaderKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("{\"Message\":\"Customer updated successfully\"}");
		} else {
			headers.add("response-code", dataNotFoundKV.get("responseCode"));
			headers.add("response-desc", dataNotFoundKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, dataNotFoundKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("{\"Message\":\"Data not found\"}");
		}
	}
	
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
		HttpHeaders headers = new HttpHeaders();
		String resp = custService.deleteCustomer(id);
		if (resp.equals("success")) {
			headers.add("response-code", successHeaderKV.get("responseCode"));
			headers.add("response-desc", successHeaderKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, successHeaderKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body("{\"Message\":\"Success\"}");
		} else {
			headers.add("response-code", dataNotFoundKV.get("responseCode"));
			headers.add("response-desc", dataNotFoundKV.get("responseDesc"));
			headers.add(HttpHeaders.CONTENT_TYPE, dataNotFoundKV.get("contentType"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("{\"Message\":\"Customer id is not found\"}");
		}
	}

}
