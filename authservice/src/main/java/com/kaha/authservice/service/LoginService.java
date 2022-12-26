package com.kaha.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kaha.authservice.model.AppUser;
import com.kaha.authservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("Load user by username..........");
		Optional<AppUser> user=userRepository.findByUsername(username);
		if(user.isPresent()) {
			
			AppUser appUser=user.get();
			System.out.println("User :" + appUser);
			List<GrantedAuthority> authorities=new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + appUser.getRole()));
			
			return new User(appUser.getUsername(),appUser.getPassword(),authorities);
			
		}
		else {
			//System.out.println("User not Found...");
			throw new UsernameNotFoundException("Not found");
		}
	}

}
