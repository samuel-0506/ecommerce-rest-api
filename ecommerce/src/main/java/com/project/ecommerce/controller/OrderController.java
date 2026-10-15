package com.project.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.modal.OrderItem;
import com.project.ecommerce.modal.Orders;
import com.project.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/customer/{customerId}")
	public Orders placeOrder(@PathVariable Long customerId) {
		return orderService.placeOrder(customerId);
	}

	@GetMapping
	public List<Orders> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/{orderId}")
	public Orders getOrderById(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}

	@GetMapping("/customer/{customerId}")
	public List<Orders> getOrdersByCustomerId(@PathVariable Long customerId) {
		return orderService.getOrdersByCustomerId(customerId);
	}

	@GetMapping("/{orderId}/items")
	public List<OrderItem> getOrderItems(@PathVariable Long orderId) {
		return orderService.getOrderItems(orderId);
	}
}