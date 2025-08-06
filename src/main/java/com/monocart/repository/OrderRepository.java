package com.monocart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocart.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	Optional<Order> findByUserId(int userId);
	
}
