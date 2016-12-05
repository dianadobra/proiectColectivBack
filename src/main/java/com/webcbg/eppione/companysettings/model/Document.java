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
	private Long guid;
	private String name;
	private DocumentStatus documentState;
	private float version;
	private String documentUrl;
	private Date creationDate;
	private Date updateDate;
	private String abstractInput;
	private String keywords;
	private boolean isSigned;
	private ApprovalState approvalStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User creator;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "signed_by_id")
	private User signedBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "zone_id")
	private DocumentZone zone;

	public enum DocumentStatus {
		Draft, Final, FinalUpdated, Blocked
	}

	public enum ApprovalState {
		Approved, Unapproved, NeedsRevision
	}

}
