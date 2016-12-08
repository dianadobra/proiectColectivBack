package com.webcbg.eppione.companysettings.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webcbg.eppione.companysettings.model.Document.ApprovalStatus;
import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;

import lombok.Data;

@Data
public class DocumentDTO {

	private String abstractInput;
	private String keywords;
	private boolean signed;
	private String name;
	private DocumentStatus documentState;
	private float version;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date updateDate;
	private ApprovalStatus approvalStatus;
	private String username;
	private String signedByName;

}
