package com.cooksys.shared.finalss;

/**
 * The POJO that contains the result film list of the requested film criteria.
 * 
 * @author George McClain
 *
 */


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cooksys.finals.Film;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmList {
	
	@XmlElement
	private List<Film> list;

	/**
	 * @return the list
	 */
	public List<Film> getFilmList() {
		return list;
	}

	/**
	 * @param filmList the list to set
	 */
	public void setFilmList(List<Film> filmList) {
		this.list = filmList;
	}

}