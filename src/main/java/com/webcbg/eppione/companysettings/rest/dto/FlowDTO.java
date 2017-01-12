package com.webcbg.eppione.companysettings.rest.dto;

import java.util.List;

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
	private List<String> documentsUrls;
	private List<Long> documentsIds;

}
