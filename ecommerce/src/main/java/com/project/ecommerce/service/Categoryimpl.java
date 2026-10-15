package com.project.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.modal.Category;
import com.project.ecommerce.repo.CategoryRepo;

@Service
public class Categoryimpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public Category addCategory(Category c) {
		return categoryRepo.save(c);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepo.findById(id).get();
	}

	@Override
	public Category updateCategory(Category c, Long id) {
		Category categoryInfoFromDB = getCategoryById(id);
		categoryInfoFromDB.setName(c.getName());
		categoryInfoFromDB.setDescription(c.getDescription());

		return categoryRepo.save(categoryInfoFromDB);
	}

	@Override
	public void deleteCategory(Long id) {
		Category categoryInfoFromDB = getCategoryById(id);
		categoryRepo.delete(categoryInfoFromDB);
	}
}
