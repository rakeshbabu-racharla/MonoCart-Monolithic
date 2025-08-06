package com.monocart.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleResourceNotFoundException(ResourceNotFoundException ex){
		
		Map<String,String> error = new HashMap<String, String>();
		error.put("error", ex.getMessage() );
		
		return new ResponseEntity<>( error , HttpStatus.NOT_FOUND);
	}
}
