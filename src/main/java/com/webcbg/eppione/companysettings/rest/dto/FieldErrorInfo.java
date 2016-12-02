package com.webcbg.eppione.companysettings.rest.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
public class FieldErrorInfo {

	private String defaultMessage;
	private String field;
	private String code;
	private Object rejectedValue;

}
