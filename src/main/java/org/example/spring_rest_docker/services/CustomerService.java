package org.example.spring_rest_docker.services;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.ArrayList;


import org.example.spring_rest_docker.models.Customer;
import org.example.spring_rest_docker.interfaces.CustomerInterface;

@Service
public class CustomerService implements CustomerInterface {
	
	private Collection<Customer> customers = new ArrayList<Customer>();
	private String response = "";
	
	@Override
	public Collection<Customer> getCustomers() {
		return this.customers;
	}
	
	@Override
	public Customer getCustomerById(Long id) {
		Customer cust = null;
		for(Customer customer: this.customers) {
			if (customer.getId() == id) {
				cust = customer;
				break;
			}
		}
		return cust;
	}
	
	@Override
	public String addCustomer(String name, String address) {
		if (this.customers.toArray().length == 0) {
			Long id = (long) 1;
			this.customers.add(new Customer(id, name, address));
			return "success";
		} else {
			for (Customer customer: customers) {
				if(customer.getName().equals(name)) {
					this.response = "error";
					break;
				}else {
					this.response = "success";
				}
			}
			
			if (this.response.equals("success")) {
				Long id = Long.parseLong(String.valueOf(this.customers.toArray().length+1));
				this.customers.add(new Customer(id, name, address));
			}
			return this.response;
		}
	}
	
	
	
	@Override
	public String updateCustomer(Long id, String name, String address) {
		Customer customer = getCustomerById(id);
		customer.setName(name);
		customer.setAddress(address);
		return "success";
	}

	@Override
	public String deleteCustomer(Long id) {
		Customer cust = null;
		for (Customer customer: customers) {
			if (customer.getId()==id) {
				this.response = "success";
				cust = customer;
				break;
			}else {
				this.response = "error";
			}
		}
		if (this.response.equals("success")) {
			this.customers.remove(cust);
		}	
		return this.response;
	}
}
