package com.project.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.modal.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
