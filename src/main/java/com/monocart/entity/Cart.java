package com.monocart.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "user_id" , unique = true)
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<CartItem> items = new ArrayList<CartItem>();
	
	private Double cartTotalPrice;
	
	public Cart() {
		super();
	}

	public double getCartTotalPrice() {
		return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
	}


	public void setCartTotalPrice(Double cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}

	public Cart(int id, User user, List<CartItem> items) {
		super();
		this.id = id;
		this.user = user;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

}
