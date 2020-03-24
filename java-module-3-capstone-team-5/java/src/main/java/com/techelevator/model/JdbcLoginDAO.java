package com.techelevator.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcLoginDAO implements LoginDAO {
	
	  private JdbcTemplate jdbcTemplate;
	  
@Autowired
public void JdbcLoginDao(DataSource dataSource) {
	   this.jdbcTemplate = new JdbcTemplate(dataSource);
	       
	 }

}
