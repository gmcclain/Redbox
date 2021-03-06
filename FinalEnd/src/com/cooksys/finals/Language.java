package com.cooksys.finals;

// Generated Aug 26, 2014 11:34:37 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Language generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Language implements java.io.Serializable {
	private Byte languageId;
    @XmlElement
	private String name;
    @XmlElement
	private Date lastUpdate;
    @XmlTransient
	private Set<Film> filmsForOriginalLanguageId = new HashSet<Film>(0);
    @XmlTransient
	private Set<Film> filmsForLanguageId = new HashSet<Film>(0);

	public Language() {
	}

	public Language(String name, Date lastUpdate) {
		this.name = name;
		this.lastUpdate = lastUpdate;
	}

	public Language(String name, Date lastUpdate,
			Set<Film> filmsForOriginalLanguageId, Set<Film> filmsForLanguageId) {
		this.name = name;
		this.lastUpdate = lastUpdate;
		this.filmsForOriginalLanguageId = filmsForOriginalLanguageId;
		this.filmsForLanguageId = filmsForLanguageId;
	}

	public Byte getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(Byte languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Film> getFilmsForOriginalLanguageId() {
		return this.filmsForOriginalLanguageId;
	}

	public void setFilmsForOriginalLanguageId(Set<Film> filmsForOriginalLanguageId) {
		this.filmsForOriginalLanguageId = filmsForOriginalLanguageId;
	}

	public Set<Film> getFilmsForLanguageId() {
		return this.filmsForLanguageId;
	}

	public void setFilmsForLanguageId(Set<Film> filmsForLanguageId) {
		this.filmsForLanguageId = filmsForLanguageId;
	}

}
