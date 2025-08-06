package com.monocart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocart.entity.Category;
import com.monocart.exception.ResourceNotFoundException;
import com.monocart.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	public Category getCategoryById(int id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
	}

	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category updateCategory(Category updatedCategory) {
		Category category = categoryRepository.findById(updatedCategory.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Category not found with id: " + updatedCategory.getId()));

		return categoryRepository.save(updatedCategory);
	}

	public void deleteCategoryById(int id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

		categoryRepository.delete(category);
	}

}
