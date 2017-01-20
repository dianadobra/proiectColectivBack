package com.webcbg.eppione.companysettings.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private ApprovalStatus approvalStatus;
	
	@ElementCollection
	@CollectionTable(name="comments", joinColumns=@JoinColumn(name="user_id"))
	@Column(name="comment")
	private List<String> comments;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User creator;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funding_id")
	private Funding funding;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "flow_document", joinColumns = {
			@JoinColumn(name = "flow_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "document_id", referencedColumnName = "id") })
	private List<Document> documents;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "flow", cascade=CascadeType.ALL)
	private List<GenericPerson> genericPersons;
	
	public enum ApprovalStatus {
		Approved, Unapproved, NeedsRevision
	}

}
