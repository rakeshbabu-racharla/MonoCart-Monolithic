package com.monocart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocart.entity.Order;
import com.monocart.enums.OrderStatus;
import com.monocart.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/get-all-orders")
	public ResponseEntity<List<Order>> getAllOrders(){
		return ResponseEntity.ok( orderService.getAllOrders() ) ;
	}
	
	@GetMapping("/get-order-by-id/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable int id){
		return ResponseEntity.ok( orderService.getOrderById(id));
	}
	
	@GetMapping("/get-order-by-userId/{id}")
	public ResponseEntity<Order> getOrderByUserId(@PathVariable int userId){
		return ResponseEntity.ok( orderService.getOrderByUserId(userId));
	}
	
	
	/*
	 * @PostMapping("/create-order-from-cart/{cartId}") public ResponseEntity<Order>
	 * createOrderByFromCart(@PathVariable int cartId) { Order newOrder =
	 * orderService.createOrderFromCart(cartId); return ResponseEntity.ok(newOrder);
	 * }
	 */
	
	
	
	@PutMapping("/update-order-status/{orderId}/status")
	public ResponseEntity<?> updateStatus(@PathVariable int orderId,
	                                      @RequestParam String status) {
	    OrderStatus newStatus;
	    try {
	        newStatus = OrderStatus.valueOf(status.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Invalid order status: " + status);
	    }

	    Order updated = orderService.updateOrderStatus(orderId, newStatus);
	    return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/delete-order-by-id/{orderId}")
	public ResponseEntity<String> deleteOrderById(@PathVariable int orderId){
		
		orderService.deleteOrderById(orderId);
		
		return ResponseEntity.ok().body("Order deleted with Id: " + orderId);
	}
	
	
}
