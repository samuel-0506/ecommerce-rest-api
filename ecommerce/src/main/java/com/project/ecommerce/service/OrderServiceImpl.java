package com.project.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ecommerce.modal.Cart;
import com.project.ecommerce.modal.CartItem;
import com.project.ecommerce.modal.Customer;
import com.project.ecommerce.modal.OrderItem;
import com.project.ecommerce.modal.OrderStatus;
import com.project.ecommerce.modal.Orders;
import com.project.ecommerce.modal.Product;
import com.project.ecommerce.repo.CartItemRepo;
import com.project.ecommerce.repo.CartRepo;
import com.project.ecommerce.repo.CustomerRepo;
import com.project.ecommerce.repo.OrderItemRepo;
import com.project.ecommerce.repo.OrdersRepo;
import com.project.ecommerce.repo.ProductRepo;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrdersRepo ordersRepo;
	private final OrderItemRepo orderItemRepo;
	private final CartRepo cartRepo;
	private final CartItemRepo cartItemRepo;
	private final CustomerRepo customerRepo;
	private final ProductRepo productRepo;

	public OrderServiceImpl(OrdersRepo ordersRepo, OrderItemRepo orderItemRepo, CartRepo cartRepo,
			CartItemRepo cartItemRepo, CustomerRepo customerRepo, ProductRepo productRepo) {

		this.ordersRepo = ordersRepo;
		this.orderItemRepo = orderItemRepo;
		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
		this.customerRepo = customerRepo;
		this.productRepo = productRepo;
	}

	@Override
	@Transactional
	public Orders placeOrder(Long customerId) {

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Cart cart = cartRepo.findByCustomerId(customerId).orElseThrow(() -> new RuntimeException("Cart not found"));

		List<CartItem> cartItems = cartItemRepo.findByCartId(cart.getId());

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		double totalAmount = 0.0;

		for (CartItem item : cartItems) {

			Product product = productRepo.findById(item.getProduct().getId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			if (item.getQuantity() == null || item.getQuantity() <= 0) {
				throw new RuntimeException("Invalid quantity for product: " + product.getName());
			}

			if (product.getStockQuantity() == null || product.getStockQuantity() < item.getQuantity()) {
				throw new RuntimeException("Insufficient stock for product: " + product.getName());
			}

			totalAmount += product.getPrice() * item.getQuantity();
		}

		Orders order = new Orders();
		order.setCustomer(customer);
		order.setTotalAmount(totalAmount);
		order.setStatus(OrderStatus.PLACED);
		order.setOrderDate(LocalDateTime.now());

		Orders savedOrder = ordersRepo.save(order);

		for (CartItem item : cartItems) {

			Product product = productRepo.findById(item.getProduct().getId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(savedOrder);
			orderItem.setProduct(product);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(product.getPrice());

			orderItemRepo.save(orderItem);

			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());

			productRepo.save(product);
		}

		cartItemRepo.deleteByCartId(cart.getId());

		return savedOrder;
	}

	@Override
	public Orders getOrderById(Long orderId) {

		return ordersRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
	}

	@Override
	public List<Orders> getAllOrders() {
		return ordersRepo.findAll();
	}

	@Override
	public List<Orders> getOrdersByCustomerId(Long customerId) {

		if (!customerRepo.existsById(customerId)) {
			throw new RuntimeException("Customer not found");
		}

		return ordersRepo.findByCustomerId(customerId);
	}

	@Override
	public List<OrderItem> getOrderItems(Long orderId) {

		getOrderById(orderId);

		return orderItemRepo.findByOrderId(orderId);
	}
}