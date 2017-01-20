package com.webcbg.eppione.companysettings.rest.dto;

import javax.validation.constraints.NotNull;

import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;

import lombok.Data;

@Data
public class LogDTO {
	@NotNull
	private String date;
	@NotNull
	private LogAction action;
	private Long entityId;
	private LogEntity entityType;
	private String description;
	@NotNull
	private String userName;
}
