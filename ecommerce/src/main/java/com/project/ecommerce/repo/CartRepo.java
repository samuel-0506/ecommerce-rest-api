package com.project.ecommerce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {

	Optional<Cart> findByCustomerId(Long customerId);

}
