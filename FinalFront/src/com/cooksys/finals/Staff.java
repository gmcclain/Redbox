package com.cooksys.finals;

// Generated Aug 25, 2014 3:34:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Staff generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Staff implements java.io.Serializable {
	@XmlElement
	private Byte staffId;
	@XmlElement
	private Address address;
	@XmlElement
	private Store store;
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;
	@XmlElement
	private byte[] picture;
	@XmlElement
	private String email;
	@XmlElement
	private boolean active;
	@XmlElement
	private String username;
	@XmlElement
	private String password;
	@XmlElement
	private Date lastUpdate;
	@XmlTransient
	private Set<Rental> rentals = new HashSet<Rental>(0);
	@XmlTransient
	private Set<Payment> payments = new HashSet<Payment>(0);
	@XmlTransient
	private Set<Store> stores = new HashSet<Store>(0);

	public Staff() {
	}

	public Staff(Address address, Store store, String firstName,
			String lastName, boolean active, String username, Date lastUpdate) {
		this.address = address;
		this.store = store;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.username = username;
		this.lastUpdate = lastUpdate;
	}

	public Staff(Address address, Store store, String firstName,
			String lastName, byte[] picture, String email, boolean active,
			String username, String password, Date lastUpdate, Set<Rental> rentals,
			Set<Payment> payments, Set<Store> stores) {
		this.address = address;
		this.store = store;
		this.firstName = firstName;
		this.lastName = lastName;
		this.picture = picture;
		this.email = email;
		this.active = active;
		this.username = username;
		this.password = password;
		this.lastUpdate = lastUpdate;
		this.rentals = rentals;
		this.payments = payments;
		this.stores = stores;
	}

	public Byte getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Byte staffId) {
		this.staffId = staffId;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Rental> getRentals() {
		return this.rentals;
	}

	public void setRentals(Set<Rental> rentals) {
		this.rentals = rentals;
	}

	public Set<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public Set<Store> getStores() {
		return this.stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

}
