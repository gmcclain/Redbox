package com.cooksys.finalss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.cooksys.finals.*;
import com.cooksys.shared.finalss.*;

/**
 * The bean that handles logged-in user's cart
 * 
 * @author George McClain
 *
 */
@Component
@Scope("session")
public class CartBean {
	@Autowired
	private LoginBean loginBean;

	private List<Film> filmsInCart;
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Adds the specified film to the cart.
	 * 
	 * @param addFilm: The film to add to the cart.
	 * @author George McClain
	 */
	public void addToCart(Film addFilm) {
		log.info("User " + loginBean.getUserName() + " added " + addFilm.getTitle() + " to cart");
		if (filmsInCart == null)
			filmsInCart = new ArrayList<Film>();
		filmsInCart.add(addFilm);
	}

	/**
	 * If the specified film is in the cart, remove it. Otherwise, do nothing.
	 * 
	 * @param removeFilm: The film to remove from the cart.
	 * @author George McClain
	 */
	public void removeFromCart(Film removeFilm) {
		log.info("User " + loginBean.getUserName() + " removed " + removeFilm.getTitle() + " from cart");
		if (filmsInCart == null)
			return;
		filmsInCart.remove(removeFilm);
	}

	/**
	 * Returns true if the film is already in the cart, if not it is false.
	 * 
	 * @param film: The film to check if it's in the cart.
	 * @return true if the film is in the cart, and false otherwise.
	 */
	public boolean isInCart(Film film) {
		log.info("User " + loginBean.getUserName() + " checked if " + film.getTitle() + " is in cart");
		if (filmsInCart == null)
			return false;
		return filmsInCart.contains(film);
	}

	/**
	 * Sends the reservation request to the restful web service. If it gets a
	 * positive response, it then gets a transaction. If it gets a negative
	 * response, it informs the logged in user. 
	 * 
	 * @return String: The URL of the page to redirect to
	 * @author George McClain
	 * @throws JAXBException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JMSException
	 */
	public String reservationRequest() throws JAXBException, MalformedURLException, IOException, JMSException {
		log.info("User " + loginBean.getUserName() + " is sending a reservation request");
		ReservationRequest requestMe = new ReservationRequest();
		requestMe.setFilmList(filmsInCart);
		requestMe.setUser(loginBean.getLoggedInUser());
		StringWriter sw = marshallReservationRequest(requestMe);
		log.debug("Create the HTTP connection");
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/FinalEnd/filmservice/reserve").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml");
		log.debug("Write to the connection");
		try (OutputStream output = connection.getOutputStream()) {
			output.write(sw.toString().getBytes());
			output.flush();
		}
		BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		log.debug("Read the response");
		StringWriter responseWriter = new StringWriter();
		String line = "";
		while ((line = response.readLine()) != null) {
			responseWriter.write(line);
		}
		responseWriter.flush();
		String xmlString = responseWriter.toString().trim();
		log.debug("Unmarshall the response");
		StringReader sr = new StringReader(xmlString);
		JAXBContext context = JAXBContext.newInstance(ReservationResponse.class, Transaction.class, Inventory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ReservationResponse reserve = (ReservationResponse) unmarshaller.unmarshal(sr);
		/*
		 * If all films are available to reserve, create the JMS message to put
		 * on the queue. Otherwise, inform the user.
		 */
		if (reserve.isReserved()) {
			log.info("User " + loginBean.getUserName() + " is sending a transaction request");
			sendMessage(reserve.getTransaction());
		} else {
			log.debug("Build the list of missing films");
			String missingFilms = "";
			for (Film film : reserve.getTransaction().getUnavailableList()){
				missingFilms += film.getTitle() + "\n";
			}
			log.debug("Display the message");
			FacesMessage msg;
			msg = new FacesMessage("Some films weren't available", missingFilms);
			FacesContext.getCurrentInstance().addMessage("checkoutError", msg);
			return null;
		}
		filmsInCart = null;
		log.info("User " + loginBean.getUserName() + " successfully sent a transaction request");
		return "search.xhtml?faces-redirect=true";
	}

	/**
	 * Sends the transaction to the JMS Queue to verify and checkout.
	 * 
	 * @param transaction: The transaction for the queue to verify.
	 * @author George McClain
	 * @throws JMSException
	 */
	public void sendMessage(Transaction transaction) throws JMSException {
		log.debug("Create a ConnectionFactory");
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		log.debug("Create a Connection");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		log.debug("Create a Session");
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		log.debug("Create the destination Queue");
		Destination destination = session.createQueue("reservations");
		log.debug("Create a MessageProducer from the Session to the Queue");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		log.debug("Create the message");
		ObjectMessage message = session.createObjectMessage();
		message.setObject(transaction);
		log.debug("Tell the producer to send the message");
		producer.send(message);
		log.debug("Close the connections");
		session.close();
		connection.close();
	}

	/**
	 * Marshals the reservation request into a form JAXB can work with, and
	 * returns the StringWriter that contains the request.
	 * 
	 * @param request: The SearchRequest to marshall
	 * @return StringWriter: The writer that sends the request
	 * @throws JAXBException
	 */
	private StringWriter marshallReservationRequest(ReservationRequest request)
			throws JAXBException {
		log.debug("Marshall Reservation request");
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(ReservationRequest.class, FilmCategory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(request, sw);
		return sw;
	}

	/**
	 * @return the filmsInCart
	 */
	public List<Film> getFilmsInCart() {
		return filmsInCart;
	}

	/**
	 * @param filmsInCart
	 *   the filmsInCart to set
	 */
	public void setFilmsInCart(List<Film> filmsInCart) {
		this.filmsInCart = filmsInCart;
	}
}