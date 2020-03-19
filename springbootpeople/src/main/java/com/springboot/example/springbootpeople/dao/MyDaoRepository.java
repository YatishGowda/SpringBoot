package com.springboot.example.springbootpeople.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.example.springbootpeople.model.Customer;

@Repository
public interface MyDaoRepository extends JpaRepository<Customer, Integer> {
	public Optional<Customer> findByUserName(String userName);

	

}
