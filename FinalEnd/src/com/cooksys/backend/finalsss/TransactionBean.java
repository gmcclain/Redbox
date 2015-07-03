package com.cooksys.backend.finalsss;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.finals.*;
import com.cooksys.shared.finalss.*;

/**
 * The bean that handles the transactions that exist in the back-end.
 * 
 * @author George McClain
 *
 */
@Component
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TransactionBean {

	@Autowired
	private SessionFactory sessionFactory;
	private long nextId = 0;
	private static Map<Transaction, Boolean> transactionList;

	/**
	 * Creates a new transaction from a film list, and adds it to the list of
	 * transactions.
	 * 
	 * @param filmList The films in the transaction
	 * @param customer The customer associated with the currently logged in user
	 * @return Transaction: The newly created transaction
	 * @author George McClain
	 */
	public Transaction newTransaction(List<Inventory> filmList,Customer customer) {
		Transaction returnMe = new Transaction();
		returnMe.setFilmList(filmList);
		returnMe.setTransactionId(getNextId());
		returnMe.setCustomer(customer);
		if (transactionList == null) {
			transactionList = new ConcurrentHashMap<Transaction, Boolean>();
		}
		transactionList.put(returnMe, true);
		System.out.println("New Transaction in " + this);
		return returnMe;
	}

	/**
	 * Returns the next available transaction ID, and increments the internal
	 * nextId variable.
	 * 
	 * @return Long: The next transaction ID
	 * @author George McClain
	 */
	public Long getNextId() {
		nextId++;
		return nextId;
	}

	/**
	 * Returns true if the transaction exists in the list of recorded
	 * transactions, otherwise it returns false. This method only checks transaction
	 * IDs.
	 * 
	 * @param checkMe: The pending transaction
	 * @return true if the transaction exists, false otherwise.
	 * @author George McClain
	 */
	public boolean isTransaction(Transaction checkMe) {
		if (transactionList == null)
			return false;
		for (Transaction t : transactionList.keySet()) {
			if (checkMe.getTransactionId() == t.getTransactionId())
				return true;
		}
		return false;
	}

	/**
	 * Adds a new rental to the rental table for all inventory objects in the
	 * Transaction object.
	 * 
	 * @param rent : The transaction object to create the new rental for.
	 * @author George McClain
	 */
	@Transactional
	public void rent(Transaction rent) {
		Session session = sessionFactory.getCurrentSession();
		//"Staff member is completely arbitrary";
		Query q = session.createQuery("from Staff s");
		Staff staff = (Staff) q.list().get(0);
		Rental newRental = null;
		for (Inventory i : rent.getFilmList()) {
			newRental = new Rental();
			newRental.setCustomer(rent.getCustomer());
			newRental.setInventory(i);
			newRental.setLastUpdate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			newRental.setRentalDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
			newRental.setStaff(staff);
			session.save(newRental);
		}
	}

	/**
	 * Checks the transaction queue to see if there are any pending transactions
	 * that have reserved the given inventory object
	 * 
	 * @param inventory: The pending inventory object 
	 * @return true if there's an existing transaction involving that inventory
	 *         object, otherwise it returns false.
	 * @author George McClain
	 */
	public boolean isReserved(Inventory inventory) {
		if (transactionList == null) 
			return false;
		for (Transaction t : transactionList.keySet()){
			for (Inventory i : t.getFilmList()){
				if (i.getInventoryId() == inventory.getInventoryId()) 
					return true;
			}
		}
		return false;
	}
}
