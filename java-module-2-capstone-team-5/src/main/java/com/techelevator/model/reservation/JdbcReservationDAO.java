package com.techelevator.model.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;



public class JdbcReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;

	public JdbcReservationDAO(SingleConnectionDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	



	/*********************************************
	 * Insert new Reservation into the database. *
	 ********************************************/
	
	@Override
	public void saveReservation(Reservation newReservation) {
		String sqlSaveReservation = "Insert into reservation(reservation_id, site_id, name, from_date, "
				+ " to_date, create_date) " + " Values(?, ?, ?, ?, ?, now())";
		newReservation.setReservationId(getNextReservationId());
		jdbcTemplate.update(sqlSaveReservation, newReservation.getReservationId(), newReservation.getSiteId(),
				newReservation.getName(), newReservation.getFromDate(), newReservation.getToDate());

	}

		

	/***************************
	 *  Map Row To Reservation *
	 **************************/
	
	public Reservation mapRowToReservation(SqlRowSet results) {
		Reservation reservation = new Reservation();
		reservation.setReservationId(results.getLong("reservation_id"));
		reservation.setSiteId(results.getInt("site_id"));
		reservation.setName(results.getString("name"));
		reservation.setFromDate(results.getDate("from_date").toLocalDate());
		reservation.setToDate(results.getDate("to_date").toLocalDate());
		reservation.setCreateDate(results.getDate("create_date").toLocalDate());

		return reservation;
	}

	
	/*******************************
	 * Get the next Reservation ID *
	 ******************************/
	
	public long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("Select nextval('reservation_reservation_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("something went wrong");
		}

	}

	
	
	/************************
	 * Create a Reservation *
	 ***********************/
	
	public Reservation createReservation(int site_id, String name, LocalDate from_date, LocalDate to_date) {
		Reservation newReservation = new Reservation();
		newReservation.setSiteId(site_id);
		newReservation.setName(name);
		newReservation.setFromDate(from_date);
		newReservation.setToDate(to_date);
		

		return newReservation;
	}





	
}
