package com.webcbg.eppione.companysettings.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Funding {

	@Id
	@GeneratedValue
	private Long id;
	private String type;

}
