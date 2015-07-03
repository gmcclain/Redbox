package com.cooksys.shared.finalss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cooksys.finals.*;

/**
 * The POJO used to request the list of rented films of a user.
 * 
 * @author George McClain
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RentedRequest {
	@XmlElement
	private User user;

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