package com.project.ecommerce.service;

import java.util.List;

import com.project.ecommerce.modal.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer);

	List<Customer> getAllCustomers();

	Customer getCustomerById(Long id);

	Customer updateCustomer(Long id, Customer customer);

	void deleteCustomer(Long id);

}
