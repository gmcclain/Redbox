package com.cooksys.shared.finalss;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The POJO that holds the response to a reservation request.
 * 
 * @author George McClain
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationResponse {
	@XmlElement
	private Transaction responseTransaction;
	@XmlElement
	private boolean isReserved;

	/**
	 * @return the transaction
	 */
	public Transaction getTransaction() {
		return responseTransaction;
	}

	/**
	 * @param transaction
	 *            the transaction to set
	 */
	public void setTransaction(Transaction transaction) {
		this.responseTransaction = transaction;
	}

	/**
	 * @return the isReserved
	 */
	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * @param isReserved
	 *            the isReserved to set
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
}