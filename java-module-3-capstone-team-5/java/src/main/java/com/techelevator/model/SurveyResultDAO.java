package com.techelevator.model;

import java.util.List;
import java.util.Map;

public interface SurveyResultDAO {
	
	public List<SurveyResult> getAllSurveyResults();
		
	public void saveSurveyResult(SurveyResult newSurveyResult);

	public List<String[]> getSurveyCount();
}
