package com.webcbg.eppione.companysettings;

import com.webcbg.eppione.BaseAcceptanceTestAT;
import com.webcbg.eppione.companysettings.model.AccountStatus;
import com.webcbg.eppione.companysettings.model.Authority;
import com.webcbg.eppione.companysettings.model.Company;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.CompanyRepository;
import com.webcbg.eppione.companysettings.repository.UserRepository;
import com.webcbg.eppione.companysettings.rest.dto.CreateCompanyDTO;
import com.webcbg.eppione.companysettings.service.CompanyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompanyAT extends BaseAcceptanceTestAT {

    private Company company;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyService companyService;

    @Before
    public void setup() {
        super.setup();
        loginAsUser("sysadmin", "test");
    }

    @After
    public void cleanup() {
        //companyRepository.delete(company);
    }

    @Test
    @Transactional
    public void whenSysadminCreatesACompany_CompanyAndAdminWithStatusNewAreCreated() {

        HttpEntity requestEntity = new HttpEntity(
                new CreateCompanyDTO("test de integrare", "admin@ti.com", "01020304"), addAuthCookies(new HttpHeaders()));
        ResponseEntity<Company> response = restTemplate.postForEntity(getApiUrl("companies"),requestEntity, Company.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        company = response.getBody();
        assertThat("Should return at least 1 company", company, not(nullValue()));
        assertThat("created company has an id", company.getId(), not(nullValue()));

        List<User> companyUsers = userRepository.findAllByCompanyId(company.getId());
        assertThat("Company should have 1 user", companyUsers, not(nullValue()));
        assertThat("Company should have admin user", companyUsers.size(), equalTo(1));

        User admin = companyUsers.get(0);
        assertThat("user is admin", admin.getAuthorities(), hasItem(Authority.COMPANY_ADMIN));
        assertThat("user is in status NEW", admin.getStatus(), equalTo(AccountStatus.NEW));
    }

}
