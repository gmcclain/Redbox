package com.cooksys.shared.finalss;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cooksys.finals.*;

/**
 * The POJO that contains the data for the reservation request. 
 * 
 * @author George McClain
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationRequest {
	@XmlElement
	private List<Film> filmList;
	@XmlElement
	private User user;

	/**
	 * @return the filmList
	 */
	public List<Film> getFilmList() {
		return filmList;
	}

	/**
	 * @param filmList the filmList to set
	 */
	public void setFilmList(List<Film> filmList) {
		this.filmList = filmList;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}