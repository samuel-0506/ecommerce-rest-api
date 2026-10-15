package com.project.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ecommerce.modal.Cart;
import com.project.ecommerce.modal.CartItem;
import com.project.ecommerce.modal.Customer;
import com.project.ecommerce.modal.Product;
import com.project.ecommerce.repo.CartItemRepo;
import com.project.ecommerce.repo.CartRepo;
import com.project.ecommerce.repo.CustomerRepo;
import com.project.ecommerce.repo.ProductRepo;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepo cartRepo;
	private final CartItemRepo cartItemRepo;
	private final CustomerRepo customerRepo;
	private final ProductRepo productRepo;

	public CartServiceImpl(CartRepo cartRepo, CartItemRepo cartItemRepo, CustomerRepo customerRepo,
			ProductRepo productRepo) {

		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
		this.customerRepo = customerRepo;
		this.productRepo = productRepo;
	}

	@Override
	public Cart createCart(Long customerId) {

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		return cartRepo.findByCustomerId(customerId).orElseGet(() -> {
			Cart cart = new Cart();
			cart.setCustomer(customer);
			cart.setCreatedAt(LocalDateTime.now());
			return cartRepo.save(cart);
		});
	}

	@Override
	public Cart getCartById(Long cartId) {

		return cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
	}

	@Override
	public Cart getCartByCustomerId(Long customerId) {

		return cartRepo.findByCustomerId(customerId).get();

	}

	@Override
	@Transactional
	public CartItem addProductToCart(Long cartId, Long productId, Integer quantity) {

		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		Cart cart = getCartById(cartId);

		Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

		if (product.getStockQuantity() == null || product.getStockQuantity() < quantity) {
			throw new RuntimeException("Insufficient product stock");
		}

		CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cartId, productId).orElseGet(() -> {
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(0);
			return newItem;
		});

		int newQuantity = cartItem.getQuantity() + quantity;

		if (product.getStockQuantity() < newQuantity) {
			throw new RuntimeException("Insufficient product stock");
		}

		cartItem.setQuantity(newQuantity);

		return cartItemRepo.save(cartItem);
	}

	@Override
	public List<CartItem> getCartItems(Long cartId) {

		getCartById(cartId);

		return cartItemRepo.findByCartId(cartId);
	}

	@Override
	@Transactional
	public CartItem updateCartItem(Long cartId, Long productId, Integer quantity) {

		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cartId, productId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		Product product = cartItem.getProduct();

		if (product.getStockQuantity() == null || product.getStockQuantity() < quantity) {
			throw new RuntimeException("Insufficient product stock");
		}

		cartItem.setQuantity(quantity);

		return cartItemRepo.save(cartItem);
	}

	@Override
	@Transactional
	public void removeProductFromCart(Long cartId, Long productId) {

		CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cartId, productId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		cartItemRepo.delete(cartItem);
	}

	@Override
	@Transactional
	public void clearCart(Long cartId) {

		getCartById(cartId);

		cartItemRepo.deleteByCartId(cartId);
	}
}