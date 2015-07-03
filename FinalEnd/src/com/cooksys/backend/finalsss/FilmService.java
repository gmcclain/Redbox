package com.cooksys.backend.finalsss;


import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cooksys.finals.*;
import com.cooksys.shared.finalss.*;
import com.cooksys.shared.finalss.FilmList;


@Controller
@RequestMapping("/filmservice")
public class FilmService {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TransactionBean transactionBean;
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Receives a request for a list of films, along with a search string of
	 * films
	 * 
	 * @param body: The body of the request to the restful web service
	 * @return String: An XML document containing the FilmList object for
	 *         the given search request.
	 * @author George McClain
	 */
	@ResponseBody
	@RequestMapping("/films")
	@Transactional
	public String filmSearch(@RequestBody String body) throws JAXBException {
		log.info("Unmarshall the request");
		StringReader sr = new StringReader(body);
		Search request = null;
		JAXBContext context = JAXBContext.newInstance(Search.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		request = (Search) unmarshaller.unmarshal(sr);
		log.info("Access the database");
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Film f join f.filmCategories fC join fC.category c where f.title like :title and c.name like :genre")
				.setParameter("title", "%" + request.getTitle() + "%")
				.setParameter("genre", "%" + request.getGenre() + "%");
		log.info("Pack the search results into a FilmList object");
		ArrayList<Film> results = new ArrayList<Film>(q.list().size());
		for (Object o : q.list()) {
			Object[] array = (Object[]) o;
			Film addMe = (Film) array[0];
			results.add(addMe);
		}
		FilmList searchResults = new FilmList();
		searchResults.setFilmList(results);
		log.info("Marshall the results and return to string");
		StringWriter sw = new StringWriter();
		context = JAXBContext.newInstance(FilmList.class, FilmActor.class, Language.class, FilmCategory.class, Inventory.class, Store.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(searchResults, sw);
		return sw.toString();
	}

	/**
	 * Checks a reservation request to see if the films are available. If they
	 * are, it creates a new transaction with the list of all films and returns
	 * it in the response. If they aren't, it creates a response with the list
	 * of unavailable films and returns it in the response.
	 * 
	 * @param body: The body of the request to the restful web service.
	 * @return String: An XML document containing the response to the request.
	 * @throws JAXBException
	 * @author George McClain
	 */
	@ResponseBody
	@RequestMapping("/reserve")
	@Transactional
	public String reserve(@RequestBody String body) throws JAXBException {
		log.info("Unmarshall the request");
		StringReader sr = new StringReader(body);
		JAXBContext context = JAXBContext.newInstance(ReservationRequest.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ReservationRequest request = (ReservationRequest) unmarshaller.unmarshal(sr);
		if (request.getFilmList() == null) {
			log.info("Received empty request");
			request.setFilmList(new ArrayList<Film>(0));
		}
		/*
		 * Check the database for each film to see if each film is available.
		 */
		Session session = sessionFactory.getCurrentSession();
		Query q;
		boolean allAvailable = true;
		List<Inventory> inventoryList = new ArrayList<Inventory>(request
				.getFilmList().size());
		List<Film> unavailableList = new ArrayList<Film>();
		for (Film film : request.getFilmList()) {
			q = session.createQuery("from Inventory i inner join i.rentals r where i.film.filmId = :filmId "
					+ "and r.inventory.inventoryId = i.inventoryId "
					+ "and not exists (from r where r.returnDate is null and r.inventory.inventoryId = i.inventoryId)");
			q.setParameter("filmId", film.getFilmId());
			/*
			 * If the returned list is null or empty, the requested film isn't
			 * available, so boolean allAvailable is set to false. Otherwise, add
			 * the first inventory object from the list of results to the list
			 * of films to rent.
			 */
			if (q.list() == null || q.list().size() < 1) {
				allAvailable = false;
				unavailableList.add(film);
			} else {
				Object[] array = (Object[]) q.list().get(0);
				inventoryList.add((Inventory) array[0]);
				System.out.println(((Inventory) array[0]).getInventoryId());
			}
		}
		/*
		 * Checks to see if any inventory items are already in the transaction
		 * queue
		 */
		if (allAvailable){
			for (Inventory i : inventoryList){
				if (transactionBean.isReserved(i)){
					allAvailable = false;
					unavailableList.add(i.getFilm());
				}
			}
		}
		/*
		 * If all films are available, create a new transaction.
		 */
		Transaction transaction;
		if (allAvailable) {
			log.info("All movies available!");			
			transaction = transactionBean.newTransaction(inventoryList, customerFromLoggedinUser(request.getUser()));
			System.out.println(transaction);
		} else {
			log.info("The following movies were not available.");
			transaction = new Transaction();
			transaction.setUnavailableList(unavailableList);
		}
		log.info("Pack the results into a ReservationResponse object.");
		ReservationResponse response = new ReservationResponse();
		response.setReserved(allAvailable);
		response.setTransaction(transaction);
		log.info("Marshall & Return the results");
		StringWriter sw = new StringWriter();
		context = JAXBContext.newInstance(ReservationResponse.class, Transaction.class,Inventory.class, Rental.class, Payment.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(response, sw);
		return sw.toString();
	}

	/**
	 * Checks to see what films the user has already rented.
	 * 
	 * @param body: The body of the request to the restful web service.
	 * @return String: An XML document containing the response to the request.
	 * @throws JAXBException
	 * @author George McClain
	 */
	@ResponseBody
	@RequestMapping("/rented")
	@Transactional
	public String rented(@RequestBody String body) throws JAXBException {
		// Unmarshall the request
		StringReader sr = new StringReader(body);
		JAXBContext context = JAXBContext.newInstance(RentedRequest.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		RentedRequest request = (RentedRequest) unmarshaller
				.unmarshal(sr);
		/*
		 * Check the database for rentals where customer ID is the same as user ID and where 
		 * return date is null
		 */
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from Inventory i join i.rentals r where r.customer.customerId = :customerId and r.returnDate is null");
		q.setParameter("customerId", request.getUser().getUserId());
		/*
		 * Copy the list of results into a non-transient list
		 */
		List<Inventory> rentalList = new ArrayList<Inventory>(q.list().size());
		for (Object o : q.list()) {
			Object[] array = (Object[]) o;
			Inventory inventory = (Inventory) array[0];
			Film tempFilm = addFilm(inventory.getFilm());
			inventory.setFilm(tempFilm);
			rentalList.add(inventory);
		}
		/*
		 * Create, Marshal & Return the rentalResults object.
		 */
		Rented result = new Rented();
		result.setInventoryList(rentalList);
		StringWriter sw = new StringWriter();
		context = JAXBContext.newInstance(Rented.class,
				Rental.class, FilmCategory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(result, sw);
		return sw.toString();
	}

	/**
	 * Updates the latest rental object in the returned inventory item to set
	 * the return date to the present.
	 * 
	 * @param body: The body of the request
	 * @return String: A blank string.
	 * @throws JAXBException
	 * @author George McClain
	 */
	@ResponseBody
	@RequestMapping("/return")
	@Transactional
	public String returnFilm(@RequestBody String body) throws JAXBException {
		log.info("Unmarshall the request");
		StringReader sr = new StringReader(body);
		JAXBContext context = JAXBContext.newInstance(Return.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Return request = (Return) unmarshaller.unmarshal(sr);
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Inventory i join i.rentals r where r.returnDate is null and r.customer.customerId = :customerId");
		q.setParameter("customerId", request.getUser().getUserId());
		/*
		 * Set the return date for the returned film and update
		 * 
		 */
		Rental rental = (Rental) (((Object[]) q.list().get(0))[1]);
		rental.setReturnDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		session.update(rental);
		return "";
	}

	/**
	 * This method starts the thread that consumes the reservation request
	 * messages from the JMS running on tcp://localhost:61616
	 * 
	 * @author George McClain
	 */
	@PostConstruct
	public void startConsumer() {
		Thread thread = new Thread(new Consumer(transactionBean));
		thread.start();
	}

	/**
	 * This method takes a User object and returns the associated Customer
	 * object as a non-transient object.
	 * 
	 * @param user: The user object whose associated customer is requested.
	 * @return Customer: The non-transient customer object associated with the user.
	 * @author George McClain
	 */
	@Transactional
	private Customer customerFromLoggedinUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from Customer c where c.customerId = :customerId");
		q.setParameter("customerId", user.getUserId());
		Customer temp = (Customer) q.list().get(0);
		Customer cust = new Customer();
		cust.setCustomerId(temp.getCustomerId());
		cust.setAddress(temp.getAddress());
		cust.setEmail(temp.getEmail());
		cust.setStore(temp.getStore());
		cust.setFirstName(temp.getFirstName());
		cust.setLastName(temp.getLastName());
		cust.setCreateDate(temp.getCreateDate());
		cust.setLastUpdate(temp.getLastUpdate());
		cust.setPayments(temp.getPayments());
		cust.setRentals(cust.getRentals());
		cust.setUsers(temp.getUsers());
		return cust;
	}

	/**
	 * Adds the film from the transient hibernate object to a 
	 * non-transient object.
	 * 
	 * @param film: The film to add to rent list
	 * @return Film: A non-transient film object
	 * @author George McClain
	 */
	private Film addFilm(Film film) {
		Film rentFilm = new Film();
		rentFilm.setDescription(film.getDescription());
		rentFilm.setFilmActors(film.getFilmActors());
		rentFilm.setFilmCategories(film.getFilmCategories());
		rentFilm.setFilmId(film.getFilmId());
		rentFilm.setInventories(film.getInventories());
		rentFilm.setLanguageByLanguageId(film.getLanguageByLanguageId());
		rentFilm.setLanguageByOriginalLanguageId(film.getLanguageByOriginalLanguageId());
		rentFilm.setLastUpdate(film.getLastUpdate());
		rentFilm.setLength(film.getLength());
		rentFilm.setRating(film.getRating());
		rentFilm.setReleaseYear(film.getReleaseYear());
		rentFilm.setRentalDuration(film.getRentalDuration());
		rentFilm.setRentalRate(film.getRentalRate());
		rentFilm.setReplacementCost(film.getReplacementCost());
		rentFilm.setSpecialFeatures(film.getSpecialFeatures());
		rentFilm.setTitle(film.getTitle());
		return rentFilm;
	}
}