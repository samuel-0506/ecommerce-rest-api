package com.project.ecommerce.service;

import java.util.List;

import com.project.ecommerce.modal.OrderItem;
import com.project.ecommerce.modal.Orders;

public interface OrderService {

	Orders placeOrder(Long customerId);

	Orders getOrderById(Long orderId);

	List<Orders> getAllOrders();

	List<Orders> getOrdersByCustomerId(Long customerId);

	List<OrderItem> getOrderItems(Long orderId);
}