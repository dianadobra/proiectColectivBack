package com.webcbg.eppione.companysettings.rest.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DepartmentDTO {
	private Long id;
	@NotNull
	private String name;
}
