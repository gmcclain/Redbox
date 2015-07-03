package com.cooksys.shared.finalss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cooksys.finals.*;

/**
 * The POJO containing the request to send to return an inventory item.
 * 
 * @author George McClain
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Return  {
	@XmlElement
	private Inventory inventory;
	@XmlElement
	private User user;

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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