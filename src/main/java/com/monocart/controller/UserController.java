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

import com.monocart.entity.User;
import com.monocart.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/get-all-users")
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok( userService.getAllUsers() );
	}
	
	@GetMapping("/get-user-by-id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id){
		return ResponseEntity.ok( userService.getUserById(id) );
	}
	
	@PostMapping("/add-user")
	public ResponseEntity<User> addUser(@RequestBody User user){
		return ResponseEntity.ok( userService.addUser(user));
	}
	
	@PutMapping("/update-user")
	public ResponseEntity<User> updateUser(@RequestBody User updatedUser){
		return ResponseEntity.ok( userService.updateUser(updatedUser) );
	}
	
	@DeleteMapping("/delete-user-by-id/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id){
		userService.deleteUserById(id);
		return ResponseEntity.ok().body("User deleted with Id: " + id );
	}
	
	
}
