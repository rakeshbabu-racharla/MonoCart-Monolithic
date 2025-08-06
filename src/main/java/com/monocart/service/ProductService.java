package com.monocart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocart.entity.Product;
import com.monocart.entity.Category;
import com.monocart.exception.ResourceNotFoundException;
import com.monocart.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not found with Id: " + id));
	}

	public List<Product> getProductByName(String name) {

		List<Product> products = productRepository.findByName(name);

		// if no products exists with given name throw exception
		if (products.isEmpty())
			throw new ResourceNotFoundException("Product Not found with Name: " + name);

		// if products list exists return products with given name
		return products;
	}

	public List<Product> getProductByCategoryId(int categoryId) {

		List<Product> products = productRepository.findByCategoryId(categoryId);

		// if no products exists with given name throw exception
		if (products.isEmpty())
			throw new ResourceNotFoundException("Product Not found with Category Id : " + categoryId);

		// if products list exists return products with given name
		return products;
	}

	public List<Product> getProductByCategoryName(String categoryName) {

		List<Product> products = productRepository.findByCategoryName(categoryName);

		// if no products exists with given name throw exception
		if (products.isEmpty())
			throw new ResourceNotFoundException("Product Not found with Category Name: " + categoryName );

		// if products list exists return products with given name
		return products;
	}

	public Product addProduct(Product product) {
		// check if product have valid category or not
		Category category = categoryService.getCategoryById( product.getCategory().getId() );
		product.setCategory(category);
		
		return productRepository.save(product);
	}
	
	public Product updateProduct(Product updateProduct) {
		
		// check if product exists or not
		Product existingProduct = productRepository.findById(updateProduct.getId())
				.orElseThrow( () -> new ResourceNotFoundException("Product Not found with Id: " + updateProduct.getId() ));
		
		// check if category is updated or not
		// if not updated assign existing category
		// else check the new category
		if( updateProduct.getCategory() == null ) {
			updateProduct.setCategory( existingProduct.getCategory() );
		} else {
			// check if category exists or not using categoryservice method getCategoryById()
			Category category = categoryService.getCategoryById( updateProduct.getCategory().getId());
			
			// if new category doesn't exist error is thrown
			// else updateProduct has valid category field
		}
		
		if( updateProduct.getName() == null || updateProduct.getName().isBlank() ) updateProduct.setName( existingProduct.getName() );
		if( updateProduct.getPrice() == null ) updateProduct.setPrice( existingProduct.getPrice() );
		if( updateProduct.getQuantity() == null ) updateProduct.setQuantity( existingProduct.getQuantity() );

		
		
		return productRepository.save(updateProduct);
	}
	
	public void deleteProductById(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("Product Not found with Id: " + id ));
		
		productRepository.delete(product);
	}

}
