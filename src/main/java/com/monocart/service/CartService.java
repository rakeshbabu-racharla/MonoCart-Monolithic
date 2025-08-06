package com.monocart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocart.entity.Cart;
import com.monocart.entity.CartItem;
import com.monocart.entity.Product;
import com.monocart.entity.User;
import com.monocart.exception.ResourceNotFoundException;
import com.monocart.repository.CartItemRepository;
import com.monocart.repository.CartRepository;
import com.monocart.repository.ProductRepository;
import com.monocart.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	public Cart getCartById(int id) {
		return cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart Not found with given Id: " + id));
	}

	public Cart getCartByUserId(int userId) {
		return cartRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("No Carts found with given User Id: " + userId));
	}

	public Cart createCartForUser(int userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

		// Check if cart already exists
		Optional<Cart> existing = cartRepository.findByUserId(userId);
		if (existing.isPresent()) {
			return existing.get();
		}
		
		
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}
	
	@Transactional
    public Cart addProductToCart(int cartId, int productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // Check if product already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item); // ✅ Save updated quantity
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem); // ✅ Save new item
        }

        // Recalculate total price
        cart.setCartTotalPrice(cart.getCartTotalPrice() );

        return cartRepository.save(cart); // ✅ Save cart to persist changes
    }
	
	@Transactional
	public Cart removeProductFromCart(int cartId, int productId) {
	    Cart cart = cartRepository.findById(cartId)
	            .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));

	    CartItem itemToRemove = cart.getItems().stream()
	            .filter(item -> item.getProduct().getId() == productId)
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart with id: " + productId));

	    // Remove from cart's item list
	    cart.getItems().remove(itemToRemove);

	    // Delete from DB
	    cartItemRepository.delete(itemToRemove);

	    // Recalculate total price
	    cart.setCartTotalPrice(cart.getCartTotalPrice());

	    return cartRepository.save(cart);
	}
	
	@Transactional
	public void deleteCart(int cartId) {
	    Cart cart = cartRepository.findById(cartId)
	            .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

	    // Delete all cart items
	    cartItemRepository.deleteAll(cart.getItems());

	    // Delete the cart itself
	    cartRepository.delete(cart);
	}

	
}
