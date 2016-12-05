package com.webcbg.eppione.companysettings.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Country {

	@Id
	@GeneratedValue
	@Access(AccessType.PROPERTY)
	private Long id;

	private String countryCode;
	private String countryName;
	private String currency;

	/**
	 * @param isoNumeric
	 * @param countryCode
	 * @param countryName
	 */
	public Country(String countryCode, String countryName, String currency) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.currency = currency;
	}
}
