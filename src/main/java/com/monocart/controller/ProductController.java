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

import com.monocart.entity.Product;
import com.monocart.entity.ProductSearchRequest;
import com.monocart.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	
	@GetMapping("/get-all-products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return ResponseEntity.ok( productService.getAllProducts() );
	}
	
	@GetMapping("/get-product-by-id/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id){
		return ResponseEntity.ok( productService.getProductById(id));
	}
	
	@GetMapping("/get-product-by-name") // name
	public ResponseEntity<List<Product>> getProductByName(@RequestBody ProductSearchRequest search ){
		return ResponseEntity.ok( productService.getProductByName( search.getName() ));
	}
	
	@GetMapping("/get-product-by-category-id") // categoryId
	public ResponseEntity<List<Product>> getProductByCategoryId(@RequestBody ProductSearchRequest search ){
		return ResponseEntity.ok( productService.getProductByCategoryId( search.getCategoryId() ));
	}
	
	@GetMapping("/get-product-by-category-name") // categoryName
	public ResponseEntity<List<Product>> getProductByCategoryName(@RequestBody ProductSearchRequest search){
		return ResponseEntity.ok( productService.getProductByCategoryName(search.getCategoryName()));
	}
	
	@PostMapping("/add-product")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		return ResponseEntity.ok( productService.addProduct(product) );
	}
	
	@PutMapping("/update-product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product updatedProduct){
		return ResponseEntity.ok( productService.updateProduct(updatedProduct) );
	}
	
	@DeleteMapping("/delete-product-by-id/{id}")
	public ResponseEntity<String> deleteProductyId(@PathVariable int id){
		productService.deleteProductById(id);
		return ResponseEntity.ok().body("Product Deleted with Id: " + id );
	}
	 
}
