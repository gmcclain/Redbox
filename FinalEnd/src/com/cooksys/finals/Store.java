package com.cooksys.finals;

// Generated Aug 26, 2014 11:34:37 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Store generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Store implements java.io.Serializable {
	@XmlElement
	private Byte storeId;
	@XmlElement
	private Staff staff;
	@XmlElement
	private Address address;
	@XmlElement
	private Date lastUpdate;
	@XmlTransient
	private Set<Inventory> inventories = new HashSet<Inventory>(0);
	@XmlTransient
	private Set<Customer> customers = new HashSet<Customer>(0);
	@XmlTransient
	private Set<Staff> staffs = new HashSet<Staff>(0);

	public Store() {
	}

	public Store(Staff staff, Address address, Date lastUpdate) {
		this.staff = staff;
		this.address = address;
		this.lastUpdate = lastUpdate;
	}

	public Store(Staff staff, Address address, Date lastUpdate,
			Set<Inventory> inventories, Set<Customer> customers, Set<Staff> staffs) {
		this.staff = staff;
		this.address = address;
		this.lastUpdate = lastUpdate;
		this.inventories = inventories;
		this.customers = customers;
		this.staffs = staffs;
	}

	public Byte getStoreId() {
		return this.storeId;
	}

	public void setStoreId(Byte storeId) {
		this.storeId = storeId;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Inventory> getInventories() {
		return this.inventories;
	}

	public void setInventories(Set<Inventory> inventories) {
		this.inventories = inventories;
	}

	public Set<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public Set<Staff> getStaffs() {
		return this.staffs;
	}

	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}

}
