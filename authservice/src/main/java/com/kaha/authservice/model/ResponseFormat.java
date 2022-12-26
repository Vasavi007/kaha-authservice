package com.kaha.authservice.model;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFormat {

	public static ResponseEntity<?> generateResponse(String message,int statusCode,Object data){
		
		
		HashMap<String,Object> response=new HashMap<>();
		response.put("Message", message);
		response.put("Status Code", statusCode);
		response.put("Data", data);
		
		return new ResponseEntity<Object>(response,HttpStatus.valueOf(statusCode));
	}
}
