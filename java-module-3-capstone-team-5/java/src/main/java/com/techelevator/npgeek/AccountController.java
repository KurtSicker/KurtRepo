package com.techelevator.npgeek;

import javax.validation.Valid;

import com.techelevator.model.UserRegistration;
import com.techelevator.model.UserRegistrationDAO;
import com.techelevator.npgeek.AuthProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AccountController
 */
@Controller
public class AccountController {
	@Autowired
	private AuthProvider auth;
	
	@Autowired
	private UserRegistrationDAO userDAO;
	
	

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String username, @RequestParam String password, @RequestParam boolean forgotPassword, RedirectAttributes flash) {
		if(forgotPassword == true) {
			if(username != null) {
				userDAO.getPasswordHintFromUsername(username);
			}
			return "redirect:/";
		}
		if (auth.signIn(username, password)) {
			return "redirect:/homePage";
		} else {
			flash.addFlashAttribute("message", "Login Invalid");
			return "redirect:/login";
		}
	}

//	@RequestMapping(path = "/loginhint", method = RequestMethod.POST)
//	public String loginHint(RedirectAttributes flash) {
//		// Figuring out how to get the actual password hint is left as an exercise to the students
//		String passwordHint = "myPasswordHint";
//		
//		flash.addFlashAttribute("passwordHint", passwordHint);
//		return "redirect:/";
//	}

	@RequestMapping(path = "/logoff", method = RequestMethod.GET)
	public String logOff() {
		auth.logOff();
		return "redirect:/";
	}

	@RequestMapping(path = "/userRegistrationInput", method = RequestMethod.GET)
	public String register(ModelMap modelHolder) {
		if (!modelHolder.containsAttribute("UserRegistration")) {
			modelHolder.put("UserRegistration", new UserRegistration());
		}
		return "register";
	}

	@RequestMapping(path = "/userRegistrationInput", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("UserRegistration") UserRegistration userRegistration // Server should
																										// validate
																										// using the
																										// "UserRegistration"
																										// , in ModelMap
																										// and
																										// UserRegistration
																										// POJO
			, BindingResult result // Result of the validation is placed in the BindingResult object
			, RedirectAttributes flash) { // Give me a flashMap so I can send the validation result and data back if
											// errors
		if (result.hasErrors()) { // If there is any error...
			flash.addFlashAttribute("UserRegistration", userRegistration); // send the data entered from
																			// UserRegistration back to view
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "UserRegistration", result); // send the validation
																									// result back to
																									// the view
			flash.addFlashAttribute("message", "Please fix the following errors:"); // send a message to be displayed
																					// concerning errors
			return "redirect:/register"; // go back to redisplay the register screen /register for GET controller
		}
		auth.register(userRegistration); // if no registration errors - run the register method
		return "redirect:/"; // go back to the home page
	}

}