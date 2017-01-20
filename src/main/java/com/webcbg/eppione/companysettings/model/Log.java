package com.webcbg.eppione.companysettings.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Log {

	@Id
	@GeneratedValue
	private Long id;
	private Date date;
	private LogAction action;
	private Long entityId;
	private LogEntity entityType;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public enum LogAction {
		Create, Update, Delete, PrepoareForDelete, Upload, ChangeStatus
	}

	public enum LogEntity {
		Document, Flow
	}

}
