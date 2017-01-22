package com.webcbg.eppione.companysettings.rest.dto;

import java.util.Date;
import java.util.List;

import com.webcbg.eppione.companysettings.model.Flow.ApprovalStatus;

import lombok.Data;

@Data
public class FlowDTO {
	
	private Long id;
	private String name;
	private boolean isActive;
	private int reviewTime;
	private String creator;
	private Long creatorId;
	private String fundingType;
	private Long fundingTypeId;
	private List<DocumentDTO> documents;
	private List<Long> documentsIds;
	private ApprovalStatus status;
	private List<String> comments;
	private String deadline;

}
