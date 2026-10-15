package com.project.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long> {

	List<Orders> findByCustomerId(Long customerId);
}
