package com.webcbg.eppione.companysettings.rest.dto;

import javax.validation.constraints.NotNull;

import com.webcbg.eppione.companysettings.model.Role;

import lombok.Data;

@Data
public class UserDTO {

	@NotNull
	private String email;
	@NotNull
	private String username;
	private String firstName;
	private String lastName;
	@NotNull
	private String password;;
	private String function;
	private Long idSuperior;
	@NotNull
	private Role role;
	private Long departmentId;

}
