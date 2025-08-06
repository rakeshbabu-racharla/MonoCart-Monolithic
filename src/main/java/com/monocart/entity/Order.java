package com.monocart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monocart.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private User user;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.NEW;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true)
	@JsonManagedReference
	private List<OrderItem> items = new ArrayList<>();

	private double totalPrice;

	public Order() {
		super();
	}

	public Order(int id, User user, LocalDateTime orderDate, OrderStatus status, List<OrderItem> items,
			double totalPrice) {
		super();
		this.id = id;
		this.user = user;
		this.orderDate = orderDate;
		this.status = status;
		this.items = items;
		this.totalPrice = totalPrice;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
