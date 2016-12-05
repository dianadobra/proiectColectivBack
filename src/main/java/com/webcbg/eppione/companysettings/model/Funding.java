package com.webcbg.eppione.companysettings.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Funding {

	@Id
	@GeneratedValue
	private Long id;
	private String type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funding")
	private List<GenericPerson> genericPersons;

}
