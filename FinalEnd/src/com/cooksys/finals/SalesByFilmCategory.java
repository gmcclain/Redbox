package com.cooksys.finals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


// Generated Aug 26, 2014 11:34:37 AM by Hibernate Tools 3.4.0.CR1

/**
 * SalesByFilmCategory generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesByFilmCategory implements java.io.Serializable {
	@XmlElement
	private SalesByFilmCategoryId id;

	public SalesByFilmCategory() {
	}

	public SalesByFilmCategory(SalesByFilmCategoryId id) {
		this.id = id;
	}

	public SalesByFilmCategoryId getId() {
		return this.id;
	}

	public void setId(SalesByFilmCategoryId id) {
		this.id = id;
	}

}
