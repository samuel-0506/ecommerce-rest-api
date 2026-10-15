package com.project.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	boolean existsByEmail(String email);

}
