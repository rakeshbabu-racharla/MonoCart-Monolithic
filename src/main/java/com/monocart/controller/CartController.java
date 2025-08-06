package com.monocart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocart.entity.AddProductToCartRequest;
import com.monocart.entity.Cart;
import com.monocart.entity.Order;
import com.monocart.service.CartService;
import com.monocart.service.OrderService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/get-all-carts")
	public ResponseEntity<List<Cart>> getAllCarts(){
		return ResponseEntity.ok( cartService.getAllCarts() );
	}
	
	@GetMapping("/get-cart-by-id/{id}")
	public ResponseEntity<Cart> getCartById(@PathVariable int id){
		return ResponseEntity.ok( cartService.getCartById(id) );
	}
	
	@GetMapping("/get-cart-by-userid/{userId}")
	public ResponseEntity<Cart> getCartByUserId(@PathVariable int userId){
		return ResponseEntity.ok( cartService.getCartByUserId(userId) );
	}
	
	@PostMapping("/create-cart-by-userId/{userId}")
    public ResponseEntity<Cart> createCartForUser(@PathVariable int userId) {
        Cart cart = cartService.createCartForUser(userId);
        return ResponseEntity.ok(cart);
    }
	 
	@PostMapping("/add-product-to-cart") // cartId , productId , quantity
	public ResponseEntity<Cart> addToCart(@RequestBody AddProductToCartRequest request) {
	    Cart cart = cartService.addProductToCart(request.getCartId(), request.getProductId(), request.getQuantity());
	    return ResponseEntity.ok(cart);
	}
	
	@DeleteMapping("/remove-product-from-cart")
	public ResponseEntity<Cart> removeFromCart(@RequestParam int cartId,
	                                           @RequestParam int productId) {
	    Cart updatedCart = cartService.removeProductFromCart(cartId, productId);
	    return ResponseEntity.ok(updatedCart);
	}
	
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<String> deleteCart(@PathVariable int cartId) {
	    cartService.deleteCart(cartId);
	    return ResponseEntity.ok("Cart deleted successfully.");
	}
	
	@PostMapping("/create-order-from-cart/{cartId}")
    public ResponseEntity<Order> placeOrderFromCart(@PathVariable int cartId) {
        try {
            Order order = orderService.createOrderFromCart(cartId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // or return an error message
        }
    }
	
	
	
}
