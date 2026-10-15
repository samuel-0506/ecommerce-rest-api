package com.project.ecommerce.service;

import java.util.List;

import com.project.ecommerce.modal.Category;

public interface CategoryService {

	Category addCategory(Category c);
	
	List<Category> getAllCategories();
	
	Category getCategoryById(Long id);

    Category updateCategory(Category c, Long id);

    void deleteCategory(Long id);
}
