package com.cooksys.shared.finalss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The POJO that contains the data about the  search request for a list of films that
 * match the given title and genre
 * 
 * @author George McClain
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Search {
	@XmlElement
	private String title;
	@XmlElement
	private String genre;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
}