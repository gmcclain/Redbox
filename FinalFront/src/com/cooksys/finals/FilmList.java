package com.cooksys.finals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


// Generated Aug 25, 2014 3:34:49 PM by Hibernate Tools 3.4.0.CR1

/**
 * FilmList generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmList implements java.io.Serializable {
	@XmlElement
	private FilmListId id;

	public FilmList() {
	}

	public FilmList(FilmListId id) {
		this.id = id;
	}

	public FilmListId getId() {
		return this.id;
	}

	public void setId(FilmListId id) {
		this.id = id;
	}

}