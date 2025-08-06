package com.monocart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocart.entity.User;
import com.monocart.exception.ResourceNotFoundException;
import com.monocart.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 
	
	
	
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(int id) {
		return userRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("User not found with Id: " + id ));
	}
	
	public User addUser(User user) {
		return userRepository.save(user);
	}
	
	public User updateUser(User user) {
		User existingUser = userRepository.findById( user.getId() )
				.orElseThrow( () -> new ResourceNotFoundException("User not found with Id: " + user.getId() ) );
		
		if( user.getName() == null || user.getName().isBlank() ) user.setName( existingUser.getName() );
		if( user.getEmail() == null || user.getEmail().isEmpty() ) user.setEmail( existingUser.getEmail() );
		
		return userRepository.save( user ); 
	}
	
	public void deleteUserById(int id) {
		User user = userRepository.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("User not found with Id: " + id ));
		
		// user exists now delete user
		userRepository.delete(user);
	}
}
