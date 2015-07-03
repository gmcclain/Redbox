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
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cooksys.finals.*;
import com.cooksys.shared.finalss.*;


/**
 * The bean that takes care of currently rented inventory items when
 * it comes down to viewing current rentals and returning them
 * 
 * @author George McClain
 *
 */
@Component
@Scope("session")
public class RentedBean {
	@Autowired
	private LoginBean loginBean;
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Gets the list of inventory items the current user has checked out and not
	 * returned from the restful web service
	 * 
	 * @return List: The list of items the current user has
	 *         checked out from inventory.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JAXBException
	 */
	public List<Inventory> getRental() throws MalformedURLException, IOException, JAXBException {
		log.info(loginBean.getUserName() + " is requesting current rentals");
		log.debug("Create the request");
		RentedRequest request = new RentedRequest();
		request.setUser(loginBean.getLoggedInUser());
		StringWriter sw = marshallRentedRequest(request);
		log.debug("Create the HTTP Connection");
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/FinalEnd/filmservice/rented").openConnection();
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
		JAXBContext context = JAXBContext.newInstance(Rented.class, Inventory.class, Film.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Rented results = (Rented) unmarshaller.unmarshal(sr);
		log.info(loginBean.getUserName() + " received these rentals");
		return results.getInventoryList();
	}

	/**
	 * Tells the back-end that the given inventory item has been returned.
	 * 
	 * @param returnMe: The film to return.
	 * @return String: The URL of the page to redirect to after clicking on the
	 *         return button
	 * @author George McClain
	 * @throws JAXBException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String returnFilm(Inventory returnMe) throws JAXBException, MalformedURLException, IOException {
		log.info(loginBean.getUserName() + " is returning item " + returnMe.getInventoryId());
		log.debug("Create the request");
		Return request = new Return();
		request.setInventory(returnMe);
		request.setUser(loginBean.getLoggedInUser());
		StringWriter sw = marshallReturn(request);		
		log.debug("Create the HTTP Connection");
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/FinalEnd/filmservice/return").openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml");
		log.debug("Write to the connection");
		try (OutputStream output = connection.getOutputStream()) {
			output.write(sw.toString().getBytes());
			output.flush();
		}
		BufferedReader response = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		log.debug("Read & ignore the response");
		StringWriter responseWriter = new StringWriter();
		String line = "";
		while ((line = response.readLine()) != null) {
			responseWriter.write(line);
		}
		responseWriter.flush();
		log.info(loginBean.getUserName() + " returned item " + returnMe.getInventoryId());
		return "rentals.xhtml?faces-redirect=true";
	}

	/**
	 * Marshals the request into a form JAXB can work with, and returns the
	 * StringWriter that contains the request.
	 * 
	 * @param request: The RentedRequest to marshal
	 * @return StringWriter: The writer that sends the request
	 * @throws JAXBException
	 */
	private StringWriter marshallRentedRequest(RentedRequest request) throws JAXBException {
		log.debug("Marshalling the RentedRequest");
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(RentedRequest.class, User.class, Inventory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(request, sw);
		return sw;
	}
	/**
	 * Marshals the request into a form JAXB can work with, and returns the
	 * StringWriter that contains the request.
	 * 
	 * @param request: The Return request to marshal
	 * @return StringWriter: The writer that sends the request
	 * @throws JAXBException
	 */
	private StringWriter marshallReturn(Return request) throws JAXBException {
		log.debug("Marshalling Return Request");
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(Return.class, User.class, Inventory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(request, sw);
		return sw;
	}
}