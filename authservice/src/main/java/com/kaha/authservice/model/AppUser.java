package com.kaha.authservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="appUser")
public class AppUser {
	
	@Id
	@GeneratedValue()
	private Integer empId;
	@NotNull
	private String name;
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String role;

}
