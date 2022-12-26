package com.kaha.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaha.authservice.config.JwtUtil;
import com.kaha.authservice.model.AppUser;
import com.kaha.authservice.model.AuthRequest;
import com.kaha.authservice.model.AuthResponse;
import com.kaha.authservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginService loginService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil util;

	@Override
	public AppUser saveUser(AppUser user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Optional<AppUser> findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public AuthResponse generateToken(AuthRequest request) throws UsernameNotFoundException {

		System.out.println("User Login............");
		String username = request.getUsername();
		Optional<AppUser> appUser = findByUsername(username);

		if (appUser.isPresent()) {
			
			UserDetails userDetails = loginService.loadUserByUsername(username);
			System.out.println("User Details :" +userDetails +"," + "User: "+ appUser);
			System.out.println("Request :"+  request);
			if (passwordEncoder.matches(request.getPassword(), appUser.get().getPassword())) {
			//if(request.getPassword().matches(appUser.get().getPassword())) {

				System.out.println("Password Matched....");
				String token = util.generateToken(userDetails);
				return new AuthResponse(username, token, "Success");
			}
			else {
				System.out.println(" Password mismatched.........");
				//return new AuthResponse(username, "", "Password Mismatched");
				throw new UsernameNotFoundException("Incorrect  Password ");

			}

		}
		else{
			System.out.println("Incorrect Username or Password .........");
			//return new AuthResponse(username, "", "Incorrect Username/Password ");
			throw new UsernameNotFoundException("Incorrect Username or Password ");
		}
		
	}

}
