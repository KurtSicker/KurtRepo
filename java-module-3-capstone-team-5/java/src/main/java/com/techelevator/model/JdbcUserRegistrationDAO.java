package com.techelevator.model;

import javax.sql.DataSource;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


@Component
public class JdbcUserRegistrationDAO implements UserRegistrationDAO {
	
	  private JdbcTemplate jdbcTemplate;
	  
@Autowired
public void JdbcUserRegistrationDao(DataSource dataSource) {
      this.jdbcTemplate = new JdbcTemplate(dataSource);
     
  }

public int getNextUserId() {
	
	SqlRowSet nextUserIdResult = jdbcTemplate.queryForRowSet("SELECT NEXTVAL('seq_userid')");
	
	if(nextUserIdResult.next()) {
		
		return nextUserIdResult.getInt(1);
	}
	else {
		throw new RuntimeException("something went wrong");
	}	
}
@Override
public String getPasswordHintFromUsername(String username) {
	
	String sqlGetPasswordHintFromUsername = "select passwordhint from userlogin where username=?";
	
	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetPasswordHintFromUsername, username);
	
	if (results.next()) {
	       
        String storedPasswordHint = results.getString("passwordhint");
        return storedPasswordHint;
}
	return null;
}

@Override
public void saveNewUserRegistration(UserRegistration newUserRegistration) {
	
	String sqlSaveNewUserRegistration = "insert into userlogin (userid, username, password, passwordhint, email)" +
								 						"values(?,?,?,?,?)";
	
	newUserRegistration.setUserId(getNextUserId());
	
	jdbcTemplate.update(sqlSaveNewUserRegistration, newUserRegistration.getUserId(), newUserRegistration.getUsername(),
			newUserRegistration.getPassword(), newUserRegistration.getPasswordhint(), newUserRegistration.getEmail());
									
}

@Override
public UserRegistration getValidUserWithPassword(String userName, String password) {
    String sqlSearchForUser = "SELECT * FROM userlogin WHERE username = ?";

    SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchForUser, userName);
    if (results.next()) {
       
        String storedPassword = results.getString("password");
        
        if (storedPassword.equals(password))
        return mapResultToUser(results);
}
return null;

}



private UserRegistration mapResultToUser(SqlRowSet results) {
	UserRegistration user = new UserRegistration();
    user.setUsername((results.getString("username")));
    user.setPassword(results.getString("password"));
    user.setPasswordhint(results.getString("passwordhint"));
    user.setEmail(results.getString("email"));
    return user;
}

	
}



