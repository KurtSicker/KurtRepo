package com.techelevator.npgeek;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.SurveyResult;
import com.techelevator.model.SurveyResultDAO;
import com.techelevator.model.UserRegistration;
import com.techelevator.model.UserRegistrationDAO;
import com.techelevator.model.Weather;
import com.techelevator.model.WeatherDAO;


@Controller
public class NPGeekController {
	
	@Autowired
	ParkDAO thePark;
	
	@Autowired
	WeatherDAO theWeather;
	
	@Autowired
	SurveyResultDAO theSurveyResult; 
	
	@Autowired
	UserRegistrationDAO theUserRegistration;
	
	@RequestMapping(path= {"/"}, method=RequestMethod.GET)
	public String displayLoginPage(HttpSession session) {
		
		return "login";
	}
	

	
	@RequestMapping(path= {"/homePage"}, method=RequestMethod.GET)
	public String displayHomePage(HttpSession session) {
		
		// Use the ParkDAO to get all the parks
		List<Park> listOfParks = thePark.getAllParks();
			
		// Put the list in a RequestMap so View (jsp) can access it
		session.setAttribute("allParks", listOfParks);		
		
		return "homePage";
	}
	
	@RequestMapping(path="/parkDetail", method=RequestMethod.GET)
	public String displayParkDetailPage(@RequestParam String parkCode, HttpSession session) {
		Park aPark = thePark.getParkByParkCode(parkCode);
		session.setAttribute("selectedPark", aPark);
		
		String preferedTemp = (String)session.getAttribute("preferedTemp");
		if (preferedTemp == null) {
			preferedTemp = "F";
			session.setAttribute("preferedTemp", preferedTemp);
		}
		List<Weather> aWeather = theWeather.getWeatherByParkCode(parkCode, preferedTemp);
		session.setAttribute("selectedWeather", aWeather);
		
		return "parkDetail";
		
	}
	
	@RequestMapping(path="/tempConversion", method=RequestMethod.GET)
	public String convertTemperature(@RequestParam String tempChoice, @RequestParam String parkCode, HttpSession session) {
		


		if (tempChoice.equals("fahrenheit"))
			session.setAttribute("preferedTemp", "F");
		else {
			session.setAttribute("preferedTemp",  "C");
		}
		return "redirect:/parkDetail?parkCode=" + parkCode;
		
	}
	
	@RequestMapping(path="/surveyInput", method=RequestMethod.GET)
	public String displaySurveyInputPage(ModelMap surveyMap) {
		if( ! surveyMap.containsAttribute("SurveyResult")) {       
			surveyMap.addAttribute("SurveyResult", new SurveyResult());  
		}
		return "surveyInput";
		
	}
	@RequestMapping(path="/surveyInput", method=RequestMethod.POST)
	public String submitSurveyForm(
			@Valid @ModelAttribute("SurveyResult") SurveyResult surveyInputValues,                                										          
	
			BindingResult result,	     									 
			RedirectAttributes flash     
	){
		if(result.hasErrors()) {                              
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "SurveyResult", result);  
			flash.addFlashAttribute("SurveyResult", surveyInputValues);
			return "redirect:/surveyInput";  
		}
		
		theSurveyResult.saveSurveyResult(surveyInputValues);
		
		
		return "redirect:/surveyRankings";
	}
	
	@RequestMapping(path="/surveyRankings", method=RequestMethod.GET)
	public String displaySurveyRankingsPage(HttpServletRequest request) {
		
		List<String[]> theList = theSurveyResult.getSurveyCount();
		request.setAttribute("ranking",theList);
		
		return "surveyRankings";
		
	}

}
