package com.webcbg.eppione;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oltud on 5/22/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EppioneApplication.class)
@WebIntegrationTest (randomPort = true)
public class BaseAcceptanceTestAT {

    @Value("${local.server.port}")
    private int port;


/*
    @Value("${local.server.port}")
    private String apiBaseUrl;
*/

    protected List<String> authCookies;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/eppione/api";
    }

    @Autowired
    private WebApplicationContext context;

    protected RestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate = new TestRestTemplate();
        //restTemplate.setRequestFactory(TestRestTemplate);
    }

    protected ResponseEntity<String> loginAsCompanyAdmin(){
        return loginAsUser("admin_newco", "test");
    }

    protected ResponseEntity<String> loginAsUser(String username, String password) {
        MultiValueMap<String, String> loginData = new LinkedMultiValueMap<>(2);
        loginData.add("username", username);
        loginData.add("password", password);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/eppione/api/login", loginData, String.class);

        HttpHeaders headers = response.getHeaders();
        authCookies = headers.get("Set-Cookie");

        return response;
    }

    protected HttpHeaders addAuthCookies(HttpHeaders headers) {
        headers.set("Cookie", authCookies.stream().collect(Collectors.joining(";")));
        return headers;
    }

    protected String getApiUrl(String resourceUrl) {
        return getBaseUrl() + "/" + resourceUrl;
    }
}
