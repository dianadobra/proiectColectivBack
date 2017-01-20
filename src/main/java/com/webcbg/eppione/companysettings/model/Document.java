package com.webcbg.eppione.companysettings.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Document {

	@Id
	@GeneratedValue
	private Long id;
	private int guid;
	private String name;
	private DocumentStatus documentState;
	private float version;
	private String documentUrl;
	private Date creationDate;
	private Date updateDate;
	private String abstractInput;
	private String keywords;
	private boolean isSigned;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "signed_by_id")
	private User signedBy;

	public enum DocumentStatus {
		Draft, Final, FinalUpdated, Blocked
	}

	

}
