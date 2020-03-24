package com.techelevator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcSurveyResultDAO implements SurveyResultDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcSurveyResultDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<SurveyResult> getAllSurveyResults() {
		
		List<SurveyResult> allSurveyResults = new ArrayList<SurveyResult>();

		String sqlGetAllSurveyResults = "select * " +
							    		"from survey_result ";
								
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllSurveyResults);

		while (results.next()) {
			SurveyResult theSurveyResult = mapRowToSurveyResults(results);
			allSurveyResults.add(theSurveyResult);
		}

		return allSurveyResults;
	}
	
	private SurveyResult mapRowToSurveyResults(SqlRowSet results) {

		SurveyResult theSurveyResult = new SurveyResult();

		theSurveyResult.setSurveyId(results.getInt("surveyid"));
		theSurveyResult.setParkCode(results.getString("parkcode"));
		theSurveyResult.setEmail(results.getString("emailaddress"));
		theSurveyResult.setState(results.getString("state"));
		theSurveyResult.setActivityLevel(results.getString("activitylevel"));
	
		return theSurveyResult;
	}

//	@Override
//	public SurveyResult createSurveyResult(String parkCode, String email, String state, String activityLevel) {
//		
//		SurveyResult newSurveyResult = new SurveyResult();
//		
//		newSurveyResult.setParkCode(parkCode);
//		newSurveyResult.setEmail(email);
//		newSurveyResult.setState(state);
//		newSurveyResult.setActivityLevel(activityLevel);
//		
//		return newSurveyResult;
//	}

	@Override
	public void saveSurveyResult(SurveyResult newSurveyResult) {
		
		String sqlSaveSurveyResult = "insert into survey_result (surveyid, parkcode, emailaddress, state, activitylevel)" +
									 "values(?,?,?,?,?)";
		
		newSurveyResult.setSurveyId(getNextSurveyId());
		jdbcTemplate.update(sqlSaveSurveyResult, newSurveyResult.getSurveyId(), newSurveyResult.getParkCode(),
							newSurveyResult.getEmail(), newSurveyResult.getState(), newSurveyResult.getActivityLevel());
										
	}
	
	@Override
	public List<String[]> getSurveyCount() {
		String sqlGetSurveyCount = "select survey_result.parkcode, count(survey_result.parkcode), park.parkname "
				+ "from survey_result "
				+ "inner join park on survey_result.parkcode = park.parkcode "
				+ "group by survey_result.parkcode, park.parkname "
				+ "order by count(survey_result.parkcode) desc";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSurveyCount);

		List<String[]> countList = new ArrayList<String[]>();
		while (results.next()) {
		String[] rank = new String[3];
		rank[0]= results.getString("parkcode");
		rank[1] = results.getString("count");
		rank[2] = results.getString("parkname");
		countList.add(rank);
		}
		

		return countList;
	}
	
	public int getNextSurveyId() {
		
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("Select nextval('seq_surveyid')");
		
		if(nextIdResult.next()) {
			
			return nextIdResult.getInt(1);
		}
		else {
			throw new RuntimeException("something went wrong");
		}	
	}


}
