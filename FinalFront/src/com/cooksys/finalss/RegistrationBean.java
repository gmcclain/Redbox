package com.cooksys.finalss;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cooksys.finals.*;
/**
 * The bean that handles registration of new users.
 * 
 * @author George McClain
 *
 */

@Component
@Scope("session")
public class RegistrationBean {
	@Autowired
	private SessionFactory sessionFactory;
	private String userName;
	private String password;
	private String password2;
	private String firstName;
	private String lastName;
	private String address;
	private String address2;
	private String district;
	private String postalCode;
	private String cityName;
	private String countryName = "United States";

	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Registers the new user in the
	 *  sakila database.
	 * 
	 * @author George McClain
	 */
	@Transactional
	public String register() {
		log.info("A new user is trying to register");
		Session session = sessionFactory.getCurrentSession();
		Query q;
		FacesMessage msg;
		/*
		 * If user name field is empty it will return a face message
		 */
		if (userName == null || userName.equalsIgnoreCase("")) {
			log.info("User name field is null!");
			msg = new FacesMessage("The user name field cannot be empty.",
					"Please enter a user name.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * if a user with that username already exists in the
		 * database it will return a face message.
		 */
		q = session.createQuery("from User u where u.username = :userName");
		q.setParameter("userName", userName);
		if (q.list() != null && q.list().size() > 0) {
			log.warn("Someone tried to reregister existing user " + userName);
			msg = new FacesMessage(
					"A user with the given user name already exists.",
					"Pick a different user name different from " + userName + ".");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * if password field  is null it will return a face message
		 */
		if (password == null || password.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter a password");
			msg = new FacesMessage("The password field cannot be empty.",
					"Please enter a password.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * If user has not entered matching passwords it will return face message
		 */
		if (!password.equals(password2)) {
			log.info(userName + " entered mismatching passwords");
			msg = new FacesMessage("The passwords do not match",
					"Check your passwords for mistypes.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * If either the first name of last name of user is not a match 
		 * or null it will return face message
		 */
		if (firstName == null || firstName.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter a first name");
			msg = new FacesMessage("The first name field cannot be empty.",
					"Please enter your first name.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		if (lastName == null || lastName.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter a last name");
			msg = new FacesMessage("The last name field cannot be empty.",
					"Please enter your last name.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * Check that the first address field is not empty
		 */
		if (address == null || address.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter an address");
			msg = new FacesMessage("The first address field cannot be empty.",
					"Please enter your street address.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		if (district == null || district.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter a district");
			msg = new FacesMessage(
					"The state/province/district field cannot be empty.",
					"Please enter your state, province, or district.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * Check that the city field isn't empty
		 */
		if (cityName == null || cityName.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter a city");
			msg = new FacesMessage("The city field cannot be empty.",
					"Please enter the city where you live.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}
		/*
		 * Check that the country field isn't empty
		 */
		if (countryName == null || countryName.equalsIgnoreCase("")) {
			log.info(userName + " forgot to enter a country");
			msg = new FacesMessage("The country field cannot be empty.",
					"Please enter the country where you live.");
			FacesContext.getCurrentInstance().addMessage("registerError", msg);
			return "";
		}

		/*
		 * If the country already exists in the database, use that country in
		 * the address. Otherwise, add the new country to the database.
		 */
		Country country;
		q = session.createQuery("from Country c where c.country = :countryName");
		q.setParameter("countryName", countryName);
		if (q.list() == null || q.list().size() < 1) {
			country = new Country();
			country.setCountry(countryName);
			country.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			session.save(country);
			log.debug("Added " + countryName + " to database");
		} else {
			log.debug(countryName + " already exists in database");
			country = (Country) q.list().get(0);
		}
		/*
		 * If the city already exists in the database, use that city in the
		 * address. Otherwise, add a new city to the database.
		 */
		City city;
		q = session.createQuery("from City c where c.city = :cityName and c.country.country = :countryName");
		q.setParameter("cityName", cityName);
		q.setParameter("countryName", countryName);
		if (q.list() == null || q.list().size() < 1) {
			log.debug("Adding " + cityName + " to database");
			city = new City();
			city.setCity(cityName);
			city.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			city.setCountry(country);
			session.save(city);
			log.debug("Added " + cityName + " to database");
		} else {
			log.debug(cityName + " already exists in database");
			city = (City) q.list().get(0);
		}
		log.debug("Create the new address");
		Address add = new Address();
		add.setAddress(address);
		add.setAddress2(address2);
		add.setDistrict(district);
		add.setPostalCode(postalCode);
		add.setPhone("");
		add.setCity(city);
		add.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		log.debug("Save the new address to the database");
		session.save(add);
		session.update(add);
		log.debug("Create the new customer object");
		Customer cust = new Customer();
		cust.setAddress(add);
		cust.setFirstName(firstName);
		cust.setLastName(lastName);
		cust.setCreateDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		cust.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		cust.setActive(true);
		log.debug("Fill in the arbitrary store field in customer");
		q = session.createQuery("from Store s where s.storeId = 1");
		Store store = (Store) q.list().get(0);
		cust.setStore(store);
		log.debug("Create the new user object for " + userName);
		User user = new User();
		user.setUsername(userName);
		user.setCustomer(cust);
		log.debug("Create the hashing password for the user");
		String salt = BCrypt.gensalt();
		user.setPassword(BCrypt.hashpw(password, salt));
		log.debug("Add the new objects to the database");
		session.save(cust);
		session.save(user);
		log.debug("Inform the user that registration was successful");
		msg = new FacesMessage("Welcome, " + userName + "!",
				"You have registered successfully.");
		FacesContext.getCurrentInstance().addMessage("registerError", msg);
		log.info(userName + " successfully registered");
		return "home.xhtml?faces-redirect=true";
	}

	/**
	 * Auto complete for the city field.
	 * 
	 * @return List: A list of cities that match the auto complete text.
	 * @author George McClain
	 */
	@Transactional
	public List<String> completeCity(String query) {
		log.debug("Performing city autocomplete for " + query);
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from City c where c.city like :cityName");
		q.setParameter("cityName", "%" + query + "%");
		List<String> cityNames = new ArrayList<String>(q.list().size());
		for (Object city : q.list()) {
			cityNames.add(((City) city).getCity());
		}
		return cityNames;
	}

	/**
	 * Performs the auto complete for the country field.
	 * 
	 * @return List: A list of countries that match the autocomplete text.
	 * @author George McClain
	 */
	@Transactional
	public List<String> completeCountry(String query) {
		log.debug("Performing country autocomplete for " + query);
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from Country c where c.country like :countryName");
		q.setParameter("countryName", "%" + query + "%");
		List<String> countryNames = new ArrayList<String>(q.list().size());
		for (Object country : q.list()) {
			countryNames.add(((Country) country).getCountry());
		}
		return countryNames;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the password2
	 */
	public String getPassword2() {
		return password2;
	}

	/**
	 * @param password2 the password2 to set
	 */
	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}