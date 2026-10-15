package com.project.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.modal.Customer;
import com.project.ecommerce.repo.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepo customerRepo;

	@Override
	public Customer addCustomer(Customer customer) {

		if (customerRepo.existsByEmail(customer.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		customer.setCreatedAt(LocalDateTime.now());

		return customerRepo.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepo.findById(id).get();
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {

		Customer existingCustomer = getCustomerById(id);

		if (customer.getEmail() != null && !customer.getEmail().equals(existingCustomer.getEmail())
				&& customerRepo.existsByEmail(customer.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setPhone(customer.getPhone());
		existingCustomer.setAddress(customer.getAddress());

		return customerRepo.save(existingCustomer);
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer existingCustomer = getCustomerById(id);
		customerRepo.delete(existingCustomer);
	}

}
