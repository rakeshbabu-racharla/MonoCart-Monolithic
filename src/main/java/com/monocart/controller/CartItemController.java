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

import com.monocart.entity.CartItem;
import com.monocart.service.CartItemService;

@RestController
@RequestMapping("/cartitem")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;
	
	@GetMapping("/get-all-cartitems")
	public ResponseEntity<List<CartItem>> getAllCartItems(){
		return ResponseEntity.ok( cartItemService.getAllCartItems() );
	}
	
	@GetMapping("/get-cartitem-by-id/{id}")
	public ResponseEntity<CartItem> getCartItemById(@PathVariable int id){
		return ResponseEntity.ok( cartItemService.getCartItemById(id));
	}
	
	@PostMapping("/add-cartitem")
	public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem){
		return ResponseEntity.ok( cartItemService.addCartItem(cartItem) );
	}
	
	@PutMapping("/update-cartitem")
	public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem updatedCartItem ){
		return ResponseEntity.ok( cartItemService.updateCartItem(updatedCartItem) );
	}
	
	@DeleteMapping("/delete-cartitem/{id}")
	public ResponseEntity<String> deleteCartItemById(@PathVariable int id){
		cartItemService.deleteCartItemById(id);
		return ResponseEntity.ok().body("Cart Item Deleted with Id: " + id );
	}
	
	
	
}
