package com.monocart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocart.entity.Cart;
import com.monocart.entity.CartItem;
import com.monocart.entity.Order;
import com.monocart.entity.OrderItem;
import com.monocart.enums.OrderStatus;
import com.monocart.exception.ResourceNotFoundException;
import com.monocart.repository.CartRepository;
import com.monocart.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Order getOrderById(int id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with given Id: " + id));
	}

	public Order getOrderByUserId(int userId) {
		return orderRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with userId: " + userId));
	}

	// Order is created only from cart ( CartController ) 
	@Transactional
	public Order createOrderFromCart(int cartId) {

		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart Not found with id: " + cartId));

		if (cart.getItems().isEmpty()) {
			throw new ResourceNotFoundException("Cart is Empty");
		}

		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(OrderStatus.NEW);

		List<OrderItem> orderItems = new ArrayList<>();
		double total = 0;

		for (CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProduct().getPrice());

			total += cartItem.getQuantity() * cartItem.getProduct().getPrice();

			orderItems.add(orderItem);
		}

		order.setTotalPrice(total);
		order.setItems(orderItems);

		// Save the order and order items in one go
		Order savedOrder = orderRepository.save(order);

		// Clear the cart
		cart.getItems().clear();
		cart.setCartTotalPrice(0.0);
		cartRepository.save(cart);

		return savedOrder;

	}

	public Order updateOrderStatus(int orderId, OrderStatus newStatus) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order Not Found with Id: " + orderId));

		order.setStatus(newStatus);
		
		return orderRepository.save( order );
	}
	
	
	public void deleteOrderById(int orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow( () -> new ResourceNotFoundException("Order Not found with Id: " + orderId ) );
		orderRepository.delete(order);
	}
	
	
}



























