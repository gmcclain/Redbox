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
 * Address generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Address implements java.io.Serializable {
	@XmlElement
	private Short addressId;
	@XmlElement
	private City city;
	@XmlElement
	private String address;
	@XmlElement
	private String address2;
	@XmlElement
	private String district;
	@XmlElement
	private String postalCode;
	@XmlElement
	private String phone;
	@XmlElement
	private Date lastUpdate;
	@XmlTransient
	private Set<Store> stores = new HashSet<Store>(0);
	@XmlTransient
	private Set<Staff> staffs = new HashSet<Staff>(0);
	@XmlTransient
	private Set<Customer> customers = new HashSet<Customer>(0);

	public Address() {
	}

	public Address(City city, String address, String district, String phone,
			Date lastUpdate) {
		this.city = city;
		this.address = address;
		this.district = district;
		this.phone = phone;
		this.lastUpdate = lastUpdate;
	}

	public Address(City city, String address, String address2, String district,
			String postalCode, String phone, Date lastUpdate, Set<Store> stores,
			Set<Staff> staffs, Set<Customer> customers) {
		this.city = city;
		this.address = address;
		this.address2 = address2;
		this.district = district;
		this.postalCode = postalCode;
		this.phone = phone;
		this.lastUpdate = lastUpdate;
		this.stores = stores;
		this.staffs = staffs;
		this.customers = customers;
	}

	public Short getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Short addressId) {
		this.addressId = addressId;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Store> getStores() {
		return this.stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public Set<Staff> getStaffs() {
		return this.staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

	public Set<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

}