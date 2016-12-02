package com.webcbg.eppione.companysettings.rest.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public  @Data class CompanyDTO {
	
	private String name;

    private String ccEmailsTimeOff;
    private String ccEmailsSickLeave;
    private String emailsForPersonalUpdatesNotifications;

    private String companyLogoImgUrl;

}
