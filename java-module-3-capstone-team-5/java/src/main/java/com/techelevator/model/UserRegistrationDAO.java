package com.techelevator.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface UserRegistrationDAO {
	
	public void saveNewUserRegistration(UserRegistration newUserRegistration);

	public UserRegistration getValidUserWithPassword(String userName, String password);
	
	public String getPasswordHintFromUsername(String username);
}
