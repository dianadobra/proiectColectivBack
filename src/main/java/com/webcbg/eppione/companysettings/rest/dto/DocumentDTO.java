package com.webcbg.eppione.companysettings.rest.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DocumentDTO {

	private float version;
	private Long authorId;
	private Date creationDate;
	private Date updateDate;
	private String abstractInput;
	private String keywords;
	private boolean isSigned;
	private Long signedById;

}
