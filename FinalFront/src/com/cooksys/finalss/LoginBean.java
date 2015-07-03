package com.cooksys.finalss;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.cooksys.finals.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The bean that handles logging in, and holds the logged in user.
 * 
 * @author George McClain
 *
 */

@Component
@Scope("session")
public class LoginBean {
	@Autowired
	private SessionFactory sessionFactory;
	private boolean loggedIn = false;
	private String userName = null;
	private String password = "";
	private User loggedInUser;
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Attempts to log in the user with the credentials provided. If successful,
	 * redirects to the search page. Otherwise, displays a message stating that
	 * the login was unsuccessful with the reason.
	 * 
	 * @author George McClain
	 * @return String: The  search URL to redirect to otherwise return null if login has failed.
	 */
	@Transactional
	public String login() {
		log.debug("Create the session & query");
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User u where u.username = :username");
		q.setParameter("username", userName);
		FacesMessage msg;
		/*
		 * If results is null or empty, the user wasn't found.
		 */
		if (q.list() == null || q.list().isEmpty()) {
			msg = new FacesMessage("UserName/Password Combination Not Found", "Makse sure username and password are correct.");
			FacesContext.getCurrentInstance().addMessage("loginError", msg);
			log.warn("A login attempt failed: userName="+userName+",\tpassword="+password);
			return "";
		}
		/*
		 * If results contains anything, it will contain exactly one item, since
		 * userIds are unique. So, check that the passwords match. If so, log in
		 * the user; otherwise, display a message.
		 */
		User checkMe = (User) q.list().get(0);
		if (checkMe.getPassword().equals(
				BCrypt.hashpw(password, checkMe.getPassword()))) {
			loggedInUser = checkMe;
			loggedIn = true;
			log.info("User " + userName + " logged in.");
			return "search.html?faces-redirect=true";
		}
		msg = new FacesMessage("User Name/Password Combination Not Found",
				"Make sure username and password are correct.");
		FacesContext.getCurrentInstance().addMessage("loginError", msg);
		log.warn("A login attempt failed: userName="+userName+",\tpassword="+password);
		return "";
	}

	/**
	 * Logs out the logged-in user
	 * 
	 * @author George McClain
	 * @return String: The URL of the page the user is redirected to after
	 *         logging out.
	 */
	public String logout() {
		log.info("User " + userName + " logged out.");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		setLoggedIn(false);
		return "home.xhtml?faces-redirect=true";
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUserName(String username) {
		this.userName = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loggedInUser
	 */
	public User getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * @param loggedInUser
	 *            the loggedInUser to set
	 */
	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
}
