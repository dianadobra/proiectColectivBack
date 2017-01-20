package com.webcbg.eppione.companysettings.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

	private Long id;
	private String abstractInput;
	private String keywords;
	private Boolean signed;
	private String name;
	private DocumentStatus documentState;
	private float version;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date updateDate;
	private String username;
	private String signedByName;
	private Long authorId;
	private int guid;
	private String downloadUrl;

}
