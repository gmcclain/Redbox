package com.cooksys.shared.finalss;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cooksys.finals.*;
/**
 * The POJO that holds the transaction data.
 * @author George McClain
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction implements Serializable{
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	private List<Inventory> filmList;
	@XmlElement
	private List<Film> unavailableList;
	@XmlElement
	private long transactionId;
	@XmlElement
	private Customer customer;
	/**
	 * @return the filmList
	 */
	public List<Inventory> getFilmList() {
		return filmList;
	}
	/**
	 * @param filmList the filmList to set
	 */
	public void setFilmList(List<Inventory> filmList) {
		this.filmList = filmList;
	}
	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/**
	 * @return the unavailableList
	 */
	public List<Film> getUnavailableList() {
		return unavailableList;
	}
	/**
	 * @param unavailableList the unavailableList to set
	 */
	public void setUnavailableList(List<Film> unavailableList) {
		this.unavailableList = unavailableList;
	}
}