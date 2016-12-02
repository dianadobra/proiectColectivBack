package com.webcbg.eppione.companysettings.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyDTO {
	String companyName;
	String adminEmail;
	String phone;

}
