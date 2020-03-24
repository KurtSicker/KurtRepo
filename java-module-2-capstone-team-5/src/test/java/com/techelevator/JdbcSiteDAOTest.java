package com.techelevator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.site.JdbcSiteDAO;

public class JdbcSiteDAOTest {

	
	private static SingleConnectionDataSource dataSource;
	private JdbcParkDAO        daoParkTest;
	private JdbcReservationDAO daoReservationTest;
	private JdbcCampgroundDAO  daoCampgroundTest;
	private JdbcSiteDAO        daoSiteTest;
	
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/CampgroundDB");
		dataSource.setUsername("postgres");

		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		daoSiteTest = new JdbcSiteDAO(dataSource);
	}

	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
