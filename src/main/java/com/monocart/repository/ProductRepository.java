package com.monocart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocart.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	
	// find by Product Name
	List<Product> findByName(String name);
	
	 
	// find Products by category id
	List<Product> findByCategoryId(int categoryId);
	
	
	// find Products by category Name
	List<Product> findByCategoryName(String categoryName);
	
	
}
