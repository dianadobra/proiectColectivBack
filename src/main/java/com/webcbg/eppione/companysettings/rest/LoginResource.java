package com.webcbg.eppione.companysettings.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginResource {

	@RequestMapping("/user")
	public Principal user(final Principal user) {
		return user;
	}
}
