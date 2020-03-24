package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.site.Site;
import com.techelevator.model.site.SiteandCampground;

public class CampgroundDAOTest {

	/********************************************************************************************
	 * 		Using this particular implementation of DataSource so that every database           *
	 * interaction is part of the same database session and hence the same database transaction *
	 *******************************************************************************************/
	
	private static SingleConnectionDataSource dataSource;
	private JdbcCampgroundDAO testCampgroundDAO;
	private JdbcParkDAO testParkDAO;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	/*********************************************************************************
	 * Before any tests are run, this method initializes the datasource for testing. *
	 ********************************************************************************/
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/testdb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		/***************************************************************************
		 * The following line disables autocommit for connections returned by this *
		 *   DataSource. This allows us to rollback any changes after each test    *
		 **************************************************************************/
		
		dataSource.setAutoCommit(false);
	}

	/********************************************************************************
	 * After all tests have finished running, this method will close the DataSource *
	 *******************************************************************************/
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/*****************************************
	* Instantiate the JdbcDAO for campground *
	*****************************************/
	
	@Before
	public void instantiateDAO() {

		testCampgroundDAO = new JdbcCampgroundDAO(dataSource);
		testParkDAO = new JdbcParkDAO(dataSource);
	}

	/******************************************************************************
	 * After each test, we rollback any changes that were made to the database so *
	 * 				that everything is clean for the next test 					  *
	 *****************************************************************************/
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/*********************************************************************************
	 * This method provides access to the DataSource for subclasses so that they can *
	 *					 instantiate a DAO for testing 								 *
	 ********************************************************************************/
	protected DataSource getDataSource() {
		return dataSource;
	}

	/*******************************************************************************
	 * Given the campground ID and the dates they are arriving and leaving the     *
	 * checkSeason() method should return true if the campground is open and false * 
	 * 							if it is not.									   *
	 ******************************************************************************/
	
	@Test
	public void check_if_campground_is_in_season() {
		BigDecimal dailyFee = new BigDecimal("222.22");
		Park thePark = createTestPark("Tester Memorial", "Moon", LocalDate.of(5000, 9, 20), 777777, 8888888,
				"A memorial to QA testers on the dark side of the Moon");
		savePark(thePark);
		Campground theCampground = createTestCampground(thePark.getParkId(), "Memorial View", "07", "11", dailyFee);
		saveCampground(theCampground);
		boolean testBool1 = testCampgroundDAO.checkSeason(theCampground.getCampgroundId(), LocalDate.of(5050, 2, 15),
				LocalDate.of(2050, 2, 20));
		assertFalse(testBool1);
		boolean testBool2 = testCampgroundDAO.checkSeason(theCampground.getCampgroundId(), LocalDate.of(5050, 8, 15),
				LocalDate.of(2050, 8, 20));
		assertTrue(testBool2);
	}

	/*****************************************************
	 * Should return a list of campgrounds by the parkId *
	 ****************************************************/
	
	@Test
	public void list_of_campgrounds_by_park_id() {
		BigDecimal dailyFee = new BigDecimal("222.22");
		Park thePark = createTestPark("Tester Memorial", "Moon", LocalDate.of(5000, 9, 20), 777777, 8888888,
				"A memorial to QA testers on the dark side of the Moon");
		savePark(thePark);
		Campground theCampground = createTestCampground(thePark.getParkId(), "Memorial View", "07", "11", dailyFee);
		saveCampground(theCampground);

		List<Campground> testCampgroundList = testCampgroundDAO.campgroundByParkId(thePark.getParkId());
		Campground savedCampground = testCampgroundList.get(0);
		assertNotNull(testCampgroundList);
		assertEquals(1, testCampgroundList.size());
		assertCampgroundsAreEqual(savedCampground, theCampground);

	}

	/**********************************************************************
	 * Get the campgroundId from the Campground name that the user inputs *
	 *********************************************************************/
	
	@Test
	public void get_campground_id_from_campground_name() {
		BigDecimal dailyFee = new BigDecimal("222.22");
		Park thePark = createTestPark("Tester Memorial", "Moon", LocalDate.of(5000, 9, 20), 777777, 8888888,
				"A memorial to QA testers on the dark side of the Moon");
		savePark(thePark);
		Campground theCampground = createTestCampground(thePark.getParkId(), "Memorial View", "07", "11", dailyFee);
		saveCampground(theCampground);

		int theId = testCampgroundDAO.getCampgroundIdFromName("Memorial View");

		assertEquals(theCampground.getCampgroundId(), theId);

	}

	/**********************************************
	 * Get the Campground by the Park Name chosen *
	 *********************************************/
	
	@Test
	public void get_campground_by_park_name() {
		BigDecimal dailyFee = new BigDecimal("222.22");
		Park thePark = createTestPark("Tester Memorial", "Moon", LocalDate.of(5000, 9, 20), 777777, 8888888,
				"A memorial to QA testers on the dark side of the Moon");
		savePark(thePark);
		Campground theCampground = createTestCampground(thePark.getParkId(), "Memorial View", "07", "11", dailyFee);
		saveCampground(theCampground);

		List<Campground> campgroundList = testCampgroundDAO.campgroundByParkName("Tester Memorial");

		Campground testCampground = campgroundList.get(0);

		assertCampgroundsAreEqual(theCampground, testCampground);

	}
	
	/****************************************************************
	 * Get the TOP (5) available campsites from user's requirements *
	 ***************************************************************/


	@Test
	public void get_available_campsites() {
		BigDecimal dailyFee = new BigDecimal("222.22");
		Park thePark = createTestPark("Tester Memorial", "Moon", LocalDate.of(5000, 9, 20), 777777, 8888888, "A memorial to QA testers on the dark side of the Moon");
		savePark(thePark);
		Campground theCampground = createTestCampground(thePark.getParkId(), "Memorial View", "07", "11", dailyFee);
		saveCampground(theCampground);
		Site theSite = createSite(theCampground.getCampgroundId(), 1, 100, false, 0, true);
		saveSite(theSite);
		List<SiteandCampground> testList = testCampgroundDAO.getAvailableCampSites(theCampground.getCampgroundId(), LocalDate.of(2020,9,15), LocalDate.of(2020,9,20));
		
		assertEquals(5, testList.size());
	}
	
	/********************************
	 * Assert campgrounds are equal *
	 * @param expected				*
	 * @param actual				*
	 *******************************/
	
	private void assertCampgroundsAreEqual(Campground expected, Campground actual) {
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getOpenFrom(), actual.getOpenFrom());
		assertEquals(expected.getOpenTo(), actual.getOpenTo());
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getDailyFee(), actual.getDailyFee());

	}

	/****************************
	 * Create a TEST campground *
	 * @param parkId			*
	 * @param name				*
	 * @param openFrom			*
	 * @param openTo			*
	 * @param dailyFee			*
	 * @return					*
	 ***************************/
	
	private Campground createTestCampground(int parkId, String name, String openFrom, String openTo,
			BigDecimal dailyFee) {
		Campground testCampground = new Campground();

		testCampground.setParkId(parkId);
		testCampground.setName(name);
		testCampground.setOpenFrom(openFrom);
		testCampground.setOpenTo(openTo);
		testCampground.setDailyFee(dailyFee);

		return testCampground;
	}
	
	/*************************
	 * Create Test Park	     *
	 * @param name			 *
	 * @param location		 *
	 * @param establishDate	 *
	 * @param area		     *
	 * @param visitors		 *
	 * @param description    *
	 * @return				 *
	 ************************/	

	private Park createTestPark(String name, String location, LocalDate establishDate, int area, int visitors,
			String description) {
		Park testPark = new Park();

		testPark.setName(name);
		testPark.setLocation(location);
		testPark.setEstablishDate(establishDate);
		testPark.setArea(area);
		testPark.setVisitors(visitors);
		testPark.setDescription(description);

		return testPark;

	}

	/************************
	 * Get the NEXT Park ID *
	 * @return				*
	 ***********************/
	
	private int getNextParkId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("select nextval('park_park_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("something went wrong");
		}
	}

	/******************
	 * Save the Park  *
	 * @param newPark *
	 *****************/
	
	public void savePark(Park newPark) {
		String sqlInsertPark = "insert into park(park_id, name, location, establish_date, area, visitors, description) "
				+ " Values(?, ?, ?, ?, ?, ?, ?)";
		newPark.setParkId(getNextParkId());
		jdbcTemplate.update(sqlInsertPark, newPark.getParkId(), newPark.getName(), newPark.getLocation(),
				newPark.getEstablishDate(), newPark.getArea(), newPark.getVisitors(), newPark.getDescription());
	}

	/******************************
	 * Get the Next Campground ID *
	 * @return					  *
	 *****************************/
	
	private int getNextCampgroundId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("select nextval('campground_campground_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("something went wrong");
		}
	}
	
	/***************************
	 * Save the New Campground *
	 * @param newCampground	   *
	 **************************/

	private void saveCampground(Campground newCampground) {
		String sqlInsertCampground = "insert into campground(campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) "
				+ " Values(?, ?, ?, ?, ?, ?)";
		newCampground.setCampgroundId(getNextCampgroundId());
		jdbcTemplate.update(sqlInsertCampground, newCampground.getCampgroundId(), newCampground.getParkId(),
				newCampground.getName(), newCampground.getOpenFrom(), newCampground.getOpenTo(),
				newCampground.getDailyFee());
	}
	
	/***********************
	 * Create Site		   *
	 * @param campgroundId *
	 * @param siteNumber   *
	 * @param maxOccupancy *
	 * @param accessible   *
	 * @param maxRvLength  *
	 * @param utilities    *
 	 * @return             *
	 **********************/

	private Site createSite( int campgroundId, int siteNumber, int maxOccupancy, boolean accessible,
			int maxRvLength, boolean utilities) {
		Site newSite = new Site();

		
		newSite.setCampgroundId(campgroundId);
		newSite.setSiteNumber(siteNumber);
		newSite.setMaxOccupancy(maxOccupancy);
		newSite.setAccessible(accessible);
		newSite.setUtilities(utilities);

		return newSite;
	}
	
	/*********************
	 * Save the New Site *
	 * @param newSite	 *
	 ********************/

	private void saveSite(Site newSite) {
		String sqlInsertSite = "insert into site(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities)"
				+ " values(?, ?, ?, ?, ?, ?, ?) ";
		newSite.setSiteId(getNextSiteId());
		jdbcTemplate.update(sqlInsertSite,newSite.getSiteId(), newSite.getCampgroundId(), newSite.getSiteNumber(),
				newSite.getMaxOccupancy(), newSite.isAccessible(), newSite.getMaxRvLength(),
				newSite.isUtilities());
	}
	
	/************************
	 * Get the Next SITE ID *
	 * @return				*
	 ***********************/

	private int getNextSiteId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("select nextval('site_site_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("something went wrong");
		}
	}
}


