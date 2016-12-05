package com.webcbg.eppione.companysettings.model;

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
public class DocumentZone {

	@Id
	@GeneratedValue
	private Long id;
	private String type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Document> documents;

}
