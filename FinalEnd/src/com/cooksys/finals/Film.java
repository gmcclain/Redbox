package com.cooksys.finals;

// Generated Aug 26, 2014 11:34:37 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Film generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Film implements java.io.Serializable {
	@XmlElement
	private Short filmId;
	@XmlTransient
	private Language languageByOriginalLanguageId;
	@XmlTransient
	private Language languageByLanguageId;
	@XmlElement
	private String title;
	@XmlElement
	private String description;
	@XmlElement
	private Date releaseYear;
	@XmlElement
	private byte rentalDuration;
	@XmlElement
	private BigDecimal rentalRate;
	@XmlElement
	private Short length;
	@XmlElement
	private BigDecimal replacementCost;
	@XmlElement
	private String rating;
	@XmlElement
	private String specialFeatures;
	@XmlElement
	private Date lastUpdate;
	@XmlTransient
	private Set<FilmActor> filmActors = new HashSet<FilmActor>(0);
	@XmlTransient
	private Set<FilmCategory> filmCategories = new HashSet<FilmCategory>(0);
	@XmlTransient
	private Set<Inventory> inventories = new HashSet<Inventory>(0);

	public Film() {
	}

	public Film(Language languageByLanguageId, String title,
			byte rentalDuration, BigDecimal rentalRate,
			BigDecimal replacementCost, Date lastUpdate) {
		this.languageByLanguageId = languageByLanguageId;
		this.title = title;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.lastUpdate = lastUpdate;
	}

	public Film(Language languageByOriginalLanguageId,
			Language languageByLanguageId, String title, String description,
			Date releaseYear, byte rentalDuration, BigDecimal rentalRate,
			Short length, BigDecimal replacementCost, String rating,
			String specialFeatures, Date lastUpdate, Set<FilmActor> filmActors,
			Set<FilmCategory> filmCategories, Set<Inventory> inventories) {
		this.languageByOriginalLanguageId = languageByOriginalLanguageId;
		this.languageByLanguageId = languageByLanguageId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replacementCost = replacementCost;
		this.rating = rating;
		this.specialFeatures = specialFeatures;
		this.lastUpdate = lastUpdate;
		this.filmActors = filmActors;
		this.filmCategories = filmCategories;
		this.inventories = inventories;
	}

	public Short getFilmId() {
		return this.filmId;
	}

	public void setFilmId(Short filmId) {
		this.filmId = filmId;
	}

	public Language getLanguageByOriginalLanguageId() {
		return this.languageByOriginalLanguageId;
	}

	public void setLanguageByOriginalLanguageId(
			Language languageByOriginalLanguageId) {
		this.languageByOriginalLanguageId = languageByOriginalLanguageId;
	}

	public Language getLanguageByLanguageId() {
		return this.languageByLanguageId;
	}

	public void setLanguageByLanguageId(Language languageByLanguageId) {
		this.languageByLanguageId = languageByLanguageId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(Date releaseYear) {
		this.releaseYear = releaseYear;
	}

	public byte getRentalDuration() {
		return this.rentalDuration;
	}

	public void setRentalDuration(byte rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return this.rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public Short getLength() {
		return this.length;
	}

	public void setLength(Short length) {
		this.length = length;
	}

	public BigDecimal getReplacementCost() {
		return this.replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return this.specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
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

	public Set<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(Set<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public Set<Inventory> getInventories() {
		return this.inventories;
	}

	public void setInventories(Set<Inventory> inventories) {
		this.inventories = inventories;
	}

}
