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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cooksys.finals.*;
import com.cooksys.shared.finalss.*;
import com.cooksys.shared.finalss.FilmList;

/**
 * The bean that handles searching for available movies to rent
 * 
 * @author George McClain
 *
 */
@Component
@Scope("session")
public class SearchBean {
	@Autowired
	private SessionFactory sessionFactory;
	private String searchTitle = "";
	private String searchGenre = "";
	private List<Film> matchingFilms;
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Creates a Search object containing the search strings for title &
	 * genre, then sends it to the FinalEnd restful web service to get the
	 * FilmList object that match. Finally, it unmarshalls the
	 * FilmList into a list
	 * 
	 * @return List: The list of films with the specified title and/or
	 *         genre.
	 * @author George McClain
	 * @throws MalformedURLException
	 * @throws MIOException
	 * @throws JAXBException
	 */
	public List<Film> getMatchingFilms() throws MalformedURLException, IOException, JAXBException {
		log.info("Searching for films matching title: " + searchTitle + "\tgenre: " + searchGenre);
		if (matchingFilms == null) {
			log.debug("Create the request object");
			Search request = new Search();
			request.setGenre(searchGenre);
			request.setTitle(searchTitle);
			log.debug("Marshall the request to XML");
			StringWriter sw = marshallSearch(request);
			log.debug("Create the HTTP connection");
			HttpURLConnection connection = (HttpURLConnection) new URL(
					"http://localhost:8080/FinalEnd/filmservice/films")
					.openConnection();
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
			JAXBContext context = JAXBContext.newInstance(FilmList.class,Film.class, FilmCategory.class, Category.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			FilmList list = (FilmList) unmarshaller.unmarshal(sr);
			matchingFilms = list.getFilmList();
		}
		log.info("found films matching title: " + searchTitle + "\tgenre: " + searchGenre);
		return matchingFilms;
	}

	/**
	 * Clears out the list of matching films, then reloads the page.
	 * 
	 * @return String: The URL of the search page.
	 */
	public String search() {
		log.info("Clicked search button");
		matchingFilms = null;
		return "search.xhtml?faces-redirect=true";
	}

	/**
	 * Marshalls the search request into a form JAXB can work with, and returns
	 * the StringWriter that contains the search request.
	 * 
	 * @param request: The Search to marshall
	 * @return StringWriter: The writer that sends the request
	 * @throws JAXBException
	 */
	private StringWriter marshallSearch(Search request)
			throws JAXBException {
		log.debug("Marshalling request");
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(Search.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(request, sw);
		return sw;
	}
	

	/**
	 * @return the searchTitle
	 */
	public String getSearchTitle() {
		return searchTitle;
	}

	/**
	 * @param searchTitle the searchTitle to set
	 */
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	/**
	 * @return the searchGenre
	 */
	public String getSearchGenre() {
		return searchGenre;
	}

	/**
	 * @param searchGenre the searchGenre to set
	 */
	public void setSearchGenre(String searchGenre) {
		this.searchGenre = searchGenre;
	}
	
}
