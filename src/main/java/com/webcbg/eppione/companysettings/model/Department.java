package com.webcbg.eppione.companysettings.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Department {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public Department(final String name) {
		this.name = name;
	}
}
