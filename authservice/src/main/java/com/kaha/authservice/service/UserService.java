package com.kaha.authservice.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kaha.authservice.model.AppUser;
import com.kaha.authservice.model.AuthRequest;
import com.kaha.authservice.model.AuthResponse;



public interface UserService {

	public AppUser saveUser(AppUser user);
	public Optional<AppUser> findByUsername(String username);
	public AuthResponse generateToken(AuthRequest request)throws UsernameNotFoundException;
}
