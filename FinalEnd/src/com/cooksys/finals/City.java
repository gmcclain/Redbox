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
 * City generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City implements java.io.Serializable {
    @XmlElement
	private Short cityId;
    @XmlElement
	private Country country;
    @XmlElement
	private String city;
    @XmlElement
	private Date lastUpdate;
    @XmlElement
	private Set<Address> addresses = new HashSet<Address>(0);

	public City() {
	}

	public City(Country country, String city, Date lastUpdate) {
		this.country = country;
		this.city = city;
		this.lastUpdate = lastUpdate;
	}

	public City(Country country, String city, Date lastUpdate, Set<Address> addresses) {
		this.country = country;
		this.city = city;
		this.lastUpdate = lastUpdate;
		this.addresses = addresses;
	}

	public Short getCityId() {
		return this.cityId;
	}

	public void setCityId(Short cityId) {
		this.cityId = cityId;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

}
