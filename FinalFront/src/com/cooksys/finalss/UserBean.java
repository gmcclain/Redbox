package com.cooksys.finalss;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.annotation.PostConstruct;

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
 * The bean that handles updating a currently logged in user's information
 * 
 * @author George McClain
 *
 */
@Component
@Scope("view")
public class UserBean {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LoginBean loginBean;
	private String userName;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String district;
	private String postalCode;
	private String cityName;
	private String countryName;
	private User user;
	private Customer customer;
	private Address address;
	private City city;
	private Country country;
	
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Initializaes al fields related to the current logged in user
	 * 
	 * @author George McClain
	 */
	@PostConstruct
	public void init() {
		log.debug("Get the user object");
		this.user = loginBean.getLoggedInUser();
		this.userName = this.user.getUsername();
		log.info("Initializing settings for user " + userName);
		log.debug("Get the customer object");
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from Customer c where c.customerId = :customerId");
		q.setParameter("customerId", user.getCustomer().getCustomerId());
		this.customer = (Customer) q.list().get(0);
		this.address = customer.getAddress();
		this.city = address.getCity();
		this.country = city.getCountry();
		log.debug("Initialize the fields");
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.address1 = address.getAddress();
		this.address2 = address.getAddress2();
		this.district = address.getDistrict();
		this.postalCode = address.getPostalCode();
		this.cityName = city.getCity();
		this.countryName = country.getCountry();
		log.debug("Close the session");
		log.info("Initialized settings for user " + userName);
		session.close();
	}

	/**
	 * Saves the changes to the database
	 * 
	 * @return String: Redirects to the search url page
	 * @author George McClain
	 */
	@Transactional
	public String save() {
		log.info("Saving settings for user " + userName);
		log.debug("Get current session");
		Session session = sessionFactory.getCurrentSession();
		/*
		 * If the country already exists in the database, use that country in
		 * the address. Otherwise, add the new country to the database.
		 */
		Query q = session.createQuery("from Country c where c.country = :countryName");
		q.setParameter("countryName", countryName);
		if (q.list() == null || q.list().size() < 1) {
			log.debug("Adding " + countryName + " to database");
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
		Query qu = session.createQuery("from City c where c.city = :cityName");
		qu.setParameter("cityName", cityName);
		if (qu.list() == null || qu.list().size() < 1) {
			log.debug("Adding city " + cityName + " to database");
			city = new City();
			city.setCity(cityName);
			city.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			Query cq = session.createQuery("from Country c where c.countryId = 1");
			Country country = (Country) cq.list().get(0);
			city.setCountry(country);
			session.save(city);
			log.debug("Added city " + cityName + " to database");
		} else {
			log.debug("City " + cityName + " already exists in database");
			city = (City) qu.list().get(0);
		}
		log.debug("Save the changes to the address");
		address.setAddress(address1);
		address.setAddress2(address2);
		address.setCity(city);
		address.setDistrict(district);
		address.setPostalCode(postalCode);
		address.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		session.update(address);
		log.debug("Save the changes to the customer");
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		session.update(customer);
		log.debug("Save the changes to the user");
		user.setCustomer(customer);
		session.update(user);
		loginBean.setLoggedInUser(user);
		log.info("Saved settings for user " + userName);
		return "search.xhtml?faces-redirect=true";
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
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
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
	 * 
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	
	/**
	 * 
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
}