package com.webcbg.eppione.companysettings.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Flow {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private boolean isActive;
	private Date deadline;
	private int reviewTime;
	private Date creationTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User creator;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funding_id")
	private Funding funding;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Document> documents;

}
