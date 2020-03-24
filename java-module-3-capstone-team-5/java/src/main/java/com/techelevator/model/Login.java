package com.techelevator.model;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Login {
	
	@NotBlank(message="A Username is required")
	@Size(min=4, max=20)
	private String username;
	
	@NotBlank(message="A Password is required")
	@Size(min=8, max=20)
	private String password;
	
	private LocalDate loginTime;

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

	public LocalDate getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDate loginTime) {
		this.loginTime = loginTime;
	}

}
