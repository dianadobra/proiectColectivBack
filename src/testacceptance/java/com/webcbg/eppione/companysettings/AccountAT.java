package com.webcbg.eppione.companysettings;

import com.webcbg.eppione.BaseAcceptanceTestAT;
import com.webcbg.eppione.companysettings.model.AccountStatus;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.rest.dto.ActivateUserDTO;
import com.webcbg.eppione.companysettings.rest.dto.CreateUserDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Tudor Olteanu on 5/27/2016.
 */
public class AccountAT extends BaseAcceptanceTestAT {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    @Transactional
    public void whenAnInvitedUserLogsIn_HeNeedsToActivateTheAccountFirstAndThenHeCanLogin() {
        ResponseEntity<String> response = loginAsUser("invited", "test");
        assertThat("Invited user should not be allowed to login", response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        List<String> accounStatus = response.getHeaders().get("X-Eppione-accountStatus");
        assertThat("AccountStatus INVITED should be present in header", accounStatus.get(0), is(AccountStatus.INVITED.toString()));

        ActivateUserDTO activateUserDTO = ActivateUserDTO.builder()
            .username("invited")
            .oldPassword("test")
            .newPassword("test123")
            .build();
        //HttpEntity requestEntity = new HttpEntity(activateUserDTO, addAuthCookies(new HttpHeaders()));
        response = restTemplate.postForEntity(getApiUrl("account/activate"), activateUserDTO, String.class);
        assertThat("user was activated with success", response.getStatusCode(), is(HttpStatus.OK));

        response = loginAsUser("invited", "test123");
        assertThat("User can login after activated", response.getStatusCode(), is(HttpStatus.OK));
    }


    @Test
    @Transactional
    public void givenACompanyAdmin_whenHeCreatesANewUser_UserWithStatusNewIsCreated() {
        ResponseEntity<String> loginResponse = loginAsCompanyAdmin();
        assertThat("Admin can login.", loginResponse.getStatusCode(), is(HttpStatus.OK));

        CreateUserDTO createUserDTO = new CreateUserDTO("testCreated", "testcreated@newco.com", "test", "created", "user", false);
        HttpEntity requestEntity = new HttpEntity(createUserDTO, addAuthCookies(new HttpHeaders()));
        ResponseEntity<User> createResponse = restTemplate.postForEntity(getApiUrl("users"), requestEntity, User.class);
        assertThat("user was created with success", createResponse.getStatusCode(), is(HttpStatus.CREATED));
        User user = createResponse.getBody();
        assertThat("Created user is new", user.getStatus(), is(AccountStatus.NEW));
        //todo add a User matcher to check all fields

        createUserDTO = new CreateUserDTO("testCreatedInvited", "testcreatedinvited@newco.com", "test", "invited", "user", false);
        requestEntity = new HttpEntity(createUserDTO, addAuthCookies(new HttpHeaders()));
        createResponse = restTemplate.postForEntity(getApiUrl("users"), requestEntity, User.class);
        assertThat("user was created with success", createResponse.getStatusCode(), is(HttpStatus.CREATED));
        user = createResponse.getBody();
        assertThat("Created user with invite option activated is in status INVITED", user.getStatus(), is(AccountStatus.INVITED));


    }

}
