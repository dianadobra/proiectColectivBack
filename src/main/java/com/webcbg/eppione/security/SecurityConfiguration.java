package com.webcbg.eppione.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.webcbg.eppione.config.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	protected SecurityConfiguration() {
		super();
	}

	@Autowired
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

	@Autowired
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

	@Autowired
	private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

	@Autowired
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	@Autowired
	private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}")
				.antMatchers("/bower_components/**").antMatchers("/i18n/**").antMatchers("/content/**")
				.antMatchers("/swagger-ui/index.html").antMatchers("/test/**").antMatchers("/api-doc/**")
				.antMatchers("/**/api-docs").antMatchers("/**/users");

	}

	@Override
	protected void configure(final AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().formLogin()
				.loginProcessingUrl("/api/login").usernameParameter("username").passwordParameter("password")
				.successHandler(ajaxAuthenticationSuccessHandler).failureHandler(ajaxAuthenticationFailureHandler)
				.permitAll().and().logout().logoutUrl("/api/logout").logoutSuccessHandler(ajaxLogoutSuccessHandler)
				.deleteCookies("JSESSIONID", "CSRF-TOKEN").permitAll().and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/users").permitAll();

		http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);

	}
}
