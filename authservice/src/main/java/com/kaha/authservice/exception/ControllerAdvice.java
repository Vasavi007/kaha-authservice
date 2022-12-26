package com.kaha.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	
}
