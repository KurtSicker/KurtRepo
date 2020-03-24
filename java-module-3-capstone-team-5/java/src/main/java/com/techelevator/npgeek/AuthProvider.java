package com.techelevator.npgeek;


import com.techelevator.model.UserRegistration;

public interface AuthProvider {
    /**
     * Returns true if a current user is logged in.
     * @return true if user is logged in
     */
    boolean isLoggedIn();

    /**
     * Returns the currently signed in user.
     * @return the currently signed in user
     */
    UserRegistration getCurrentUser();

    /**
     * Signs in a user using the given username and password
     * @param username the given username
     * @param password the given password
     * @return true if user was successfully signed in
     */
    boolean signIn(String username, String password);

    /**
     * Sign out the currently signed in user
     */
    void logOff();

   

    /**
     * Register a new user to the system
     * @param username the new user's username
     * @param password the new user's password
     * @param role the new user's role
     */
    public void register(UserRegistration newUserRegistration);

   
}
