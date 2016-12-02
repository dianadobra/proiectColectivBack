package com.webcbg.eppione.companysettings.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Address {

	private String street;
	private String city;
	private String zipCode;
	private String phone;
	private String country;
}
