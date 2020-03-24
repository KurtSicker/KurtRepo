package com.techelevator.model.site;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JdbcSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcSiteDAO(SingleConnectionDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean isSiteAvailable() {
		
		return false;
	} 
	
	
	
	
	
	}








	