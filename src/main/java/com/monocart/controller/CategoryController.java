package com.monocart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocart.entity.Category;
import com.monocart.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/get-all-category")
	public ResponseEntity<List<Category>> getAllCategory() {
		return ResponseEntity.ok(categoryService.getAllCategory());
	}

	@GetMapping("/get-category-by-id/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@PostMapping("/add-category")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		return ResponseEntity.ok(categoryService.addCategory(category));
	}

	@PutMapping("/update-category")
	public ResponseEntity<Category> updateCategory(@RequestBody Category updatedCategory) {
		return ResponseEntity.ok(categoryService.updateCategory(updatedCategory));
	}

	@DeleteMapping("/delete-category-by-id/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable int id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.ok().body("Category Deleted with id: " + id);
	}

}
