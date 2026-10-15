package com.project.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.modal.Cart;
import com.project.ecommerce.modal.CartItem;
import com.project.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/customer/{customerId}")
	public Cart createCart(@PathVariable Long customerId) {
		return cartService.createCart(customerId);
	}

	@GetMapping("/{cartId}")
	public Cart getCartById(@PathVariable Long cartId) {
		return cartService.getCartById(cartId);
	}

	@GetMapping("/customer/{customerId}")
	public Cart getCartByCustomerId(@PathVariable Long customerId) {
		return cartService.getCartByCustomerId(customerId);
	}

	@PostMapping("/{cartId}/items")
	public CartItem addProductToCart(@PathVariable Long cartId, @RequestParam Long productId,
			@RequestParam Integer quantity) {

		return cartService.addProductToCart(cartId, productId, quantity);
	}

	@GetMapping("/{cartId}/items")
	public List<CartItem> getCartItems(@PathVariable Long cartId) {
		return cartService.getCartItems(cartId);
	}

	@PutMapping("/{cartId}/items/{productId}")
	public CartItem updateCartItem(@PathVariable Long cartId, @PathVariable Long productId,
			@RequestParam Integer quantity) {

		return cartService.updateCartItem(cartId, productId, quantity);
	}

	@DeleteMapping("/{cartId}/items/{productId}")
	public String removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {

		cartService.removeProductFromCart(cartId, productId);

		return "Product removed from cart successfully";
	}

	@DeleteMapping("/{cartId}/items")
	public String clearCart(@PathVariable Long cartId) {

		cartService.clearCart(cartId);

		return "Cart cleared successfully";
	}
}
