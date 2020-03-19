package com.springboot.example.springbootpeople.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.example.springbootpeople.model.Customer;
import com.springboot.example.springbootpeople.service.MyService;

@RestController
public class MyController {

	@Autowired
	MyService service;

	@RequestMapping(value= "/api/customer/all", method= RequestMethod.GET)
	public List<Customer> getCustomer() {
		System.out.println(this.getClass().getSimpleName() + " - Get all customer service is invoked.");
		return service.getCustomer();
	}

	@RequestMapping(value= "/api/customer/{id}", method= RequestMethod.GET)
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Get customer details by id is invoked.");

		Optional<Customer> cust =  service.getCustomerById(id);
		if(!cust.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(cust.get(),HttpStatus.OK);
	}

	@RequestMapping(value= "/api/customer/add", method= RequestMethod.POST)
	public ResponseEntity<Customer> createCustomers(@RequestBody Customer newCust, HttpServletResponse response) {
		System.out.println(this.getClass().getSimpleName() + " - Create new customer method is invoked.");
		if(newCust!=null) {
			String password = newCust.getPassword();
			Optional<Customer> cust =  service.getCustomerByUserName(newCust.getUserName());
			if(password.length()>=8 && password.length()<=10 && !cust.isPresent()) {
				Customer customer = service.addNewCustomer(newCust);
				return new ResponseEntity<>(customer, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value= "/api/customer/update/{id}", method= RequestMethod.PUT)
	public Customer updateCustomers(@RequestBody Customer updCust, @PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Update customer details by id is invoked.");

		Optional<Customer> cust =  service.getCustomerById(id);
		if (!cust.isPresent())
			throw new Exception("Could not find customer with id- " + id);

		/* To prevent the overiding of the existing value of the variables in the database, 
		 * if that variable is not coming in the @RequestBody annotation object. */		
		if(updCust.getFirstName() == null || updCust.getFirstName().isEmpty())
			updCust.setFirstName(cust.get().getFirstName());

		if(updCust.getLastName() == null || updCust.getLastName().isEmpty())
			updCust.setLastName(cust.get().getLastName());

		// Required for the "where" clause in the sql query template.
		updCust.setId(id);
		return service.updateCustomer(updCust);
	}

	@RequestMapping(value= "/api/customers/delete/{id}", method= RequestMethod.DELETE)
	public void deleteCustomersById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete customer by id is invoked.");

		Optional<Customer> cust =  service.getCustomerById(id);
		if(!cust.isPresent())
			throw new Exception("Could not find customer with id- " + id);

		service.deleteCustomerById(id);
	}

	@RequestMapping(value= "/api/customer/deleteall", method= RequestMethod.DELETE)
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all customer is invoked.");
		service.deleteAllCustomer();
	}

}
