package com.monocart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocart.entity.Cart;
import com.monocart.entity.CartItem;
import com.monocart.entity.Product;
import com.monocart.exception.ResourceNotFoundException;
import com.monocart.repository.CartItemRepository;
import com.monocart.repository.CartRepository;
import com.monocart.repository.ProductRepository;

@Service
public class CartItemService {
 
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	public List<CartItem> getAllCartItems(){
		return cartItemRepository.findAll();
	}
	
	
	public CartItem getCartItemById(int id) {
		return cartItemRepository.findById( id )
				.orElseThrow( () -> new ResourceNotFoundException("CartItem not found with Id: " + id ));
	}
	
	public CartItem addCartItem(CartItem cartItem) {
		
		Cart cart = cartRepository.findById(cartItem.getCart().getId())
				.orElseThrow( () -> new ResourceNotFoundException("Cart Not found with Id: " + cartItem.getCart().getId() ) );
		cartItem.setCart(cart);
		
		Product product = productRepository.findById(cartItem.getProduct().getId())
				.orElseThrow( () -> new ResourceNotFoundException("Product not found with given Id " + cartItem.getProduct().getId() ) );
		cartItem.setProduct(product);
		
		return cartItemRepository.save( cartItem );
	}
	
	public CartItem updateCartItem(CartItem updatedCartItem) {
		CartItem existingCartItem = cartItemRepository.findById( updatedCartItem.getId() )
				.orElseThrow( () -> new ResourceNotFoundException("Cart Item not found with Id: " + updatedCartItem.getId() ) );
		
		if( updatedCartItem.getProduct() == null ) updatedCartItem.setProduct( existingCartItem.getProduct() );
		if( updatedCartItem.getQuantity() == null ) updatedCartItem.setQuantity( existingCartItem.getQuantity() );
		if( updatedCartItem.getCart() == null ) updatedCartItem.setCart( existingCartItem.getCart() );
		
		Product product = productRepository.findById( updatedCartItem.getProduct().getId() )
				.orElseThrow( () -> new ResourceNotFoundException("Product not found with given Id " + updatedCartItem.getProduct().getId())) ;
		updatedCartItem.setProduct(product);
		
		Cart cart = cartRepository.findById(updatedCartItem.getCart().getId() )
				.orElseThrow( () -> new ResourceNotFoundException("Cart Not found with Id: " + updatedCartItem.getCart().getId()));
		updatedCartItem.setCart(cart);
		
		return cartItemRepository.save(updatedCartItem);
		
	}
	
	
	public void deleteCartItemById(int id) {
		CartItem cartItem = cartItemRepository.findById( id )
				.orElseThrow( () -> new ResourceNotFoundException("CartItem not found with Id: " + id ));
		cartItemRepository.delete(cartItem);
	}
	
	
}
