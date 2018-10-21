package org.example.spring_rest_docker.interfaces;

import java.util.Collection;
import org.example.spring_rest_docker.models.Customer;

public interface CustomerInterface {

	//getCustomers method is used to return list of customers
	public Collection<Customer> getCustomers();
	
	//getCustomer method is used to return single object of customer by its id
	public Customer getCustomerById(Long id);
	
	//addCustomer method is used to add new customer
	public String addCustomer(String name, String address);
	
	//updateCustomer method is used to update the existing customer data
	public String updateCustomer(Long id, String name, String address);
	
	//deleteCustomer method is used to delete existing customer
	public String deleteCustomer(Long id);
	
}
