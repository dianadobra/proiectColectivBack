package com.webcbg.eppione.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		final String lowercaseLogin = login.toLowerCase();

		Optional<User> userFromDatabase = userRepository.findOneByUsername(lowercaseLogin);
		if (!userFromDatabase.isPresent()) {
			userFromDatabase = userRepository.findOneByEmail(lowercaseLogin);
		}

		return userFromDatabase.map(user -> {

			final List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
					.map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					grantedAuthorities);

		}).orElseThrow(
				() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " + "database"));
	}
}
