package com.cooksys.finals;

// Generated Aug 25, 2014 3:34:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Actor generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Actor implements java.io.Serializable {
	@XmlElement
	private Short actorId;
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;
	@XmlElement
	private Date lastUpdate;
	@XmlTransient
	private Set<FilmActor> filmActors = new HashSet<FilmActor>(0);

	public Actor() {
	}

	public Actor(String firstName, String lastName, Date lastUpdate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdate = lastUpdate;
	}

	public Actor(String firstName, String lastName, Date lastUpdate,
			Set<FilmActor> filmActors) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdate = lastUpdate;
		this.filmActors = filmActors;
	}

	public Short getActorId() {
		return this.actorId;
	}

	public void setActorId(Short actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(Set<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

}
