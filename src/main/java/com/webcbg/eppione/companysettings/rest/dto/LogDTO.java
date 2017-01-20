package com.webcbg.eppione.companysettings.rest.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;
import com.webcbg.eppione.companysettings.model.User;

import lombok.Data;

@Data
public class LogDTO {
	@NotNull
	private Date date;
	@NotNull
	private LogAction action;
	private Long entityId;
	private LogEntity entityType;
	private String description;
	@NotNull
	private User user;
}
