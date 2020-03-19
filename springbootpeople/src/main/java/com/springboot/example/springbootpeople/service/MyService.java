package com.springboot.example.springbootpeople.service;

import java.util.List;
import java.util.Optional;

import com.springboot.example.springbootpeople.model.Customer;

public interface MyService {

	public List<Customer> getCustomer();
	public Optional<Customer> getCustomerById(int id);
	public Customer addNewCustomer(Customer cust);
	public Customer updateCustomer(Customer cust);
	public void deleteCustomerById(int id);
	public void deleteAllCustomer();
	public Optional<Customer> getCustomerByUserName(String userName);
	
	
}
