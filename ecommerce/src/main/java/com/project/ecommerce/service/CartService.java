package com.project.ecommerce.service;

import java.util.List;

import com.project.ecommerce.modal.Cart;
import com.project.ecommerce.modal.CartItem;

public interface CartService {

	Cart createCart(Long customerId);

	Cart getCartById(Long cartId);

	Cart getCartByCustomerId(Long customerId);

	CartItem addProductToCart(Long cartId, Long productId, Integer quantity);

	List<CartItem> getCartItems(Long cartId);

	CartItem updateCartItem(Long cartId, Long productId, Integer quantity);

	void removeProductFromCart(Long cartId, Long productId);

	void clearCart(Long cartId);

}
