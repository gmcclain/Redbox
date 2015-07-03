package com.cooksys.finals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// Generated Aug 25, 2014 3:34:49 PM by Hibernate Tools 3.4.0.CR1

/**
 * User generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements java.io.Serializable {
	@XmlElement
	private Short userId;
	@XmlElement
	private Customer customer;
	@XmlElement
	private String username;
	@XmlElement
	private String password;

	public User() {
	}

	public User(Customer customer, String username, String password) {
		this.customer = customer;
		this.username = username;
		this.password = password;
	}

	public Short getUserId() {
		return this.userId;
	}

	public void setUserId(Short userId) {
		this.userId = userId;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}