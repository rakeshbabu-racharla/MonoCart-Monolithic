package com.monocart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocart.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer >{

	
}
