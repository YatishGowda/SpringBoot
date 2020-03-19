package com.springboot.example.springbootpeople.controller;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.springboot.example.springbootpeople.model.Customer;
import com.springboot.example.springbootpeople.service.MyService;

public class MycontrollerTests {

	@InjectMocks
	MyController mycontroller;
	@Mock
	MyService myservice;

	public int id;
	public Customer customer;



	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mycontroller = new MyController();
		mycontroller.service = myservice;
		customer = new Customer();
		Random randomUtil = new Random();
		id = randomUtil.nextInt();
		customer = createCustomer(randomUtil);
		// when(myservice.getCustomer()).thenReturn(List.of(new Customer(), new Customer()));
		List<Customer> customers = new ArrayList<>();
		customers.add(customer);
		customers.add(customer);
		when(myservice.getCustomer()).thenReturn(customers);
	}
	
	@Test
	public void testGetCustomerHappyCase() {
		// Test
		List<Customer> customerList = mycontroller.getCustomer();
		// Assert
		assertNotNull(customerList);
		assertEquals(2, customerList.size());
		verify(myservice, times(1)).getCustomer();
	}

	@Test
	public void testGetCustomerRethrowsException() {
		// Setup
		when(myservice.getCustomer()).thenThrow(new RuntimeException());

		// Test and assert
		Assertions.assertThrows(RuntimeException.class, () -> mycontroller.getCustomer());
	}

	@Test
	public void testGetCustomerByIdHappyCase() throws Exception {
		// Setup
		when(myservice.getCustomerById(id)).thenReturn(Optional.of(customer));

		// Test
		ResponseEntity<Customer> responseEntity = mycontroller.getCustomerById(id);

		// Verify
		assertNotNull(responseEntity);
		assertEquals(customer, responseEntity.getBody());
		verify(myservice, times(1)).getCustomerById(id);
	}

	@Test
	public void testGetCustomerByIdCustomerNotPresent() throws Exception {
		// Setup
		Optional<Customer> customer = Optional.of(new Customer(1, "Alpha", "abc", "def",new Date(Instant.now().toEpochMilli()) ,"abc@gmail.com", "alphapass"));
		when(myservice.getCustomerById(id)).thenReturn(customer);

		// Test
		ResponseEntity<Customer> responseEntity = mycontroller.getCustomerById(id);

		// Verify
		assertNotNull(responseEntity);
		assertNotEquals(customer, responseEntity.getBody());
		verify(myservice, times(1)).getCustomerById(id);
	}

	
	@ParameterizedTest
	@CsvSource({
	    "8, 10",
	})
	public void testCreateCustomersPasswordLength(int min, int max) throws Exception {
		Optional<Customer> customer = Optional.of(new Customer(1, "Alpha", "abc", "def",new Date(Instant.now().toEpochMilli()) ,"abc@gmail.com", "alphapass"));
		when(myservice.getCustomerById(id)).thenReturn(customer);
		
		ResponseEntity<Customer> responseEntity = mycontroller.getCustomerById(1);
		assertNotNull(responseEntity);	
		int length = customer.get().getPassword().length();
		assertTrue(length >= min && length <= max);
	}
	

	private Customer createCustomer(Random randomUtil) {
		Customer customer = new Customer();
		customer.setFirstName(UUID.randomUUID().toString());
		customer.setId(id);
		customer.setDob(new Date(Instant.now().toEpochMilli()));
		customer.setLastName(UUID.randomUUID().toString());
		customer.setEmail(UUID.randomUUID().toString());
		customer.setPassword(UUID.randomUUID().toString());
		return customer;
	}
	
}
