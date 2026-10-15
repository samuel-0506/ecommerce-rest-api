package com.project.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByOrderId(Long orderId);
}