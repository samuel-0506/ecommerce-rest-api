package com.project.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
