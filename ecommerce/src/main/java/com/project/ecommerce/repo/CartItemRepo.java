package com.project.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

	List<CartItem> findByCartId(Long cartId);

	Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

	void deleteByCartId(Long cartId);

}