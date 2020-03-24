package com.techelevator.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserRegistration {
	
	private int userId;
	
	@NotBlank(message="A Username is required")
	@Size(min=4, max=20)
	private String username;
	
	@NotBlank(message="A Password is required")
	@Size(min=8, max=20)
	private String password;
	
	@NotBlank(message="Password Hint is required") 
	@Size(min=5, max=30)
	private String passwordhint;
	
	@NotBlank(message="Email address is required")   
	@Email(message="Email must be a valid email address")
	private String email;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordhint() {
		return passwordhint;
	}
	public void setPasswordhint(String passwordhint) {
		this.passwordhint = passwordhint;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
