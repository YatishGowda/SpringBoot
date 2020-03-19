package com.springboot.example.springbootpeople.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.example.springbootpeople.dao.MyDaoRepository;
import com.springboot.example.springbootpeople.model.Customer;

@Service
public class MyServiceImpl implements MyService {
	
	@Autowired
	MyDaoRepository dao;

	@Override
	public List<Customer> getCustomer() {
		return dao.findAll();
	}

	@Override
	public Optional<Customer> getCustomerById(int id) {
		return dao.findById(id);
	}

	@Override
	public Customer addNewCustomer(Customer cust) {
		return dao.save(cust);
	}

	@Override
	public Customer updateCustomer(Customer cust) {
		return dao.save(cust);
	}

	@Override
	public void deleteCustomerById(int id) {
		dao.deleteById(id);
	}

	@Override
	public void deleteAllCustomer() {
		dao.deleteAll();
	}

	@Override
	public Optional<Customer> getCustomerByUserName(String userName) {
		return dao.findByUserName(userName);
	}
}