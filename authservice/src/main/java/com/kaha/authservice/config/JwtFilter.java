package com.kaha.authservice.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kaha.authservice.service.LoginService;

@Configuration
public class JwtFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private JwtUtil util;

	@Autowired
	private LoginService loginService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("Validating Second request.....");
		String header=request.getHeader("Authorization");
		
		if(header!=null && header.startsWith("Bearer")) {
			
			String token=header.substring(7);
			String username=util.extractUsername(token);
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
				UserDetails userDetails=loginService.loadUserByUsername(username);
				System.out.println("User details :" + userDetails);
				
				if(util.validateToken(token, userDetails)) {
					
					System.out.println("Token valid....");
					UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
							userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
				}
				
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
