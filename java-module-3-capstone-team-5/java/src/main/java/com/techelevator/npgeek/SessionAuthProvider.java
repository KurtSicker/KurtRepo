package com.techelevator.npgeek;

import java.util.Arrays;

import javax.servlet.http.HttpSession;


import com.techelevator.model.UserRegistration;
import com.techelevator.model.UserRegistrationDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/************************************************************************************
 * Some background information:
 * 
 * A Java Bean is a POJO. (i.e. A Java class that follows the standard for beans)
 * 
 * A Spring bean is an object that is instantiated, assembled, and otherwise 
 * managed by a Spring IoC container.
 * 
 * A Spring IoC Container is used to managing the complete life cycle 
 * of a Spring bean from creation to its destruction. 
 * 
 * It uses Dependency Injection (DI) to manage components and 
 * Spring bean objects.
 * 
 * Configuration metadata which represent Java code, annotations 
 * or XML along with Java POJO classes are used by a Spring IoC Container
 * 
 ************************************************************************************/
/************************************************************************************
 * A bean (some code) can be session scoped allowing sharing of the bean and it's information
 * (i.e. instantiated once per session and reused 
 *       rather than instantiated when called and discarded)
 *       
 * The @Scope(value="session".....) annotation is used to give a bean session scope
 ************************************************************************************/

/**
 * SessionAuthProvider
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)	// Automatically instantiate this when a session starts
public class SessionAuthProvider implements AuthProvider {

    public static final String USER_KEY = "appCurrentUser";

    private HttpSession session;		// Define a session map to use for this class - shared by all users of the class
    private UserRegistrationDAO dao;				// Define the DAO for access to the user tables

    @Autowired							// Spring MVC will automatically inject the data source from the springmvc-servlet
    public SessionAuthProvider(HttpSession session, UserRegistrationDAO dao) {	// ctor for the class expecting a session map and DAO
        this.session = session;			// assign the session map passed to the class session map 
        this.dao = dao;					// assign the DAO passed to the class DAO
    }

    @Override
    public boolean isLoggedIn() {	// if entry in the session map is null - the user is not logged in
        return session.getAttribute(USER_KEY) != null;	// get from "appCurrentUser" the session map, the value from the key
    }

    @Override
    public UserRegistration getCurrentUser() {
        return (UserRegistration) session.getAttribute(USER_KEY);	// Return the User object from the Session map or null if user not logged-in
    }

    @Override
    public boolean signIn(String username, String password) {
        UserRegistration authenticatedUser = dao.getValidUserWithPassword(username, password);
        if (authenticatedUser != null) {	// if username and password are good... user is authenticated
            session.setAttribute(USER_KEY, authenticatedUser); // if user is authenticated - put user object with USER_KEY in session map
            return true;	// sign-in was successful
        } else {
            return false;
        }
    }

    @Override
    public void logOff() {
        session.removeAttribute(USER_KEY);
        session.invalidate();
    }



    @Override
    public void register(UserRegistration newUserRegistration) {
        dao.saveNewUserRegistration(newUserRegistration);
    }



    
}