package com.kaha.authservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaha.authservice.model.AppUser;
import com.kaha.authservice.model.AuthRequest;
import com.kaha.authservice.model.AuthResponse;
import com.kaha.authservice.model.ResponseFormat;
import com.kaha.authservice.service.UserServiceImpl;

@RequestMapping()
@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PutMapping("/user")
	public ResponseEntity<?> userRegister(@Valid @RequestBody AppUser user, BindingResult result) throws BindException {

		System.out.println("user Registeration....");

		if (!result.hasErrors()) {
			AppUser appUser = userService.saveUser(user);
			if (appUser != null) {
				return ResponseFormat.generateResponse("Account Created", HttpStatus.OK.value(), appUser);
			}
		}

		System.out.println(result.getAllErrors());
		return ResponseFormat.generateResponse("Account not Created", HttpStatus.OK.value(), result.getAllErrors());
	}

	@PostMapping("/user")
	public Object userLogin(@RequestBody AuthRequest request) throws UsernameNotFoundException {

		System.out.println("User Login Controller.......");
		
		//authenticationManager
			//	.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		AuthResponse response =null;
		try {

			response = userService.generateToken(request);
			return ResponseFormat.generateResponse("Token Generated", HttpStatus.OK.value(), response);
		} 
		catch (UsernameNotFoundException e) {
			System.out.println("Token not Generated");
			return ResponseFormat.generateResponse("Token not Generated", HttpStatus.NOT_FOUND.value(), e.getMessage());
		}

	}
	
	@GetMapping("/homeUser")
	@PreAuthorize("hasRole('Student')")
	public String welcomeUser(@RequestHeader String Authorization) {
		
		System.out.println("Student page...");
		return "Welcome to Student Home Page";
	}
	
	
	@GetMapping("/homeAdmin")
	@PreAuthorize("hasRole('Admin')")
	public String welcomeAdmin(@RequestHeader String Authorization) {
		
		System.out.println("Admin page...");
		return "Welcome to Admin Home Page";
	}

}
