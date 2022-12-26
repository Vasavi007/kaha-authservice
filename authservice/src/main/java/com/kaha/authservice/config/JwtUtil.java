package com.kaha.authservice.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JwtUtil {

	private String secretKey="cove";
	private int expireTime=1000*60*10;
	
	public String extractUsername(String token) {
		return getClaims(token).getSubject();
		
	}
	
	
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	public String generateToken(UserDetails userDetails) {
		
		Map<String,Object> claims=new HashMap<>();
		claims.put("issuer", "cove");
		
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
					  .setIssuedAt(new Date(System.currentTimeMillis()))
				      .setExpiration(new Date(System.currentTimeMillis()+ expireTime))
				      .signWith(SignatureAlgorithm.HS256, secretKey).compact();
		
	}
	public boolean validateToken(String token,UserDetails details) {
		
		String username=extractUsername(token);
		Claims claims=getClaims(token);
		return username.equals(details.getUsername()) && !claims.getExpiration().before(new Date());
	
		
	}
	
}
