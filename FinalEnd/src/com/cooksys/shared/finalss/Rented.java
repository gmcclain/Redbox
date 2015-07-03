package com.cooksys.shared.finalss;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cooksys.finals.*;

/**
 * The POJO containing the list of inventory items the user has currently rented
 * out and not returned.
 * 
 * @author George McClain
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rented {
	@XmlElement
	private List<Inventory> inventoryList;

	/**
	 * @return the inventoryList
	 */
	public List<Inventory> getInventoryList() {
		return inventoryList;
	}

	/**
	 * @param inventoryList the inventoryList to set
	 */
	public void setInventoryList(List<Inventory> inventoryList) {
		this.inventoryList = inventoryList;
	}
}