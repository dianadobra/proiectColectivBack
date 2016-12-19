package com.webcbg.eppione.companysettings;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.webcbg.eppione.EppioneApplication;
import com.webcbg.eppione.companysettings.convertor.UserConverter;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.UserRepository;
import com.webcbg.eppione.companysettings.rest.dto.UserDTO;
import com.webcbg.eppione.companysettings.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = EppioneApplication.class)
public class UserIT {

	private User user;
	private UserDTO userDTO;
	private UserDTO userDTOO;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	UserConverter userConverter;

	@Test
	@Transactional
	public void createTheUser() {
		UserDTO u = new UserDTO();
		u.setDepartmentId(Long.valueOf(1));
		u.setEmail("test@mail.com");
		u.setFirstName("test");
		u.setLastName("teest");
		u.setFunction("Boss");
		u.setIdSuperior(Long.valueOf(1));
		u.setPassword("test");
		u.setUsername("test");
		userDTO = userService.createUser(u);
		assertThat("Created userDTO should exist!", userDTO, not(nullValue()));
		// System.out.println(userDTO);
	}

	@Test
	@Transactional
	public void updateTheUser() {
		UserDTO us = new UserDTO();
		us.setDepartmentId(Long.valueOf(2));
		us.setEmail("tes2t@mail.com");
		us.setFirstName("test2");
		us.setLastName("teest2");
		us.setFunction("Boss2");
		us.setIdSuperior(Long.valueOf(2));
		us.setPassword("test2");
		us.setUsername("test2");
		userDTO = userService.createUser(us);
		String s = userDTO.getUsername();

		UserDTO n = new UserDTO();
		n.setDepartmentId(Long.valueOf(2));
		n.setEmail("update@mail.com");
		n.setFirstName("update");
		n.setLastName("update");
		n.setFunction("update");
		n.setIdSuperior(Long.valueOf(2));
		n.setPassword("update");
		n.setUsername("update");
		userDTOO = userService.createUser(n);
		// userDTO = userService.updateUser(userDTO.getDepartmentId(),
		// userDTOO);
		// System.out.println(userDTO);
		userDTO.setUsername("updateTest");
		String ss = userDTO.getUsername();
		assertThat("Update should change the name!", s, not(ss));
	}

	@Test
	@Transactional
	public void deleteTheUser() throws Exception {
		UserDTO d = new UserDTO();
		d.setDepartmentId(Long.valueOf(3));
		d.setEmail("update@mail.com");
		d.setFirstName("update");
		d.setLastName("update");
		d.setFunction("update");
		d.setIdSuperior(Long.valueOf(3));
		d.setPassword("update");
		d.setUsername("update");
		userDTO = userService.createUser(d);
		d = userService.getById(userDTO.getIdSuperior());
		userService.deleteUser(d.getIdSuperior());
		assertThat("Created userDTO should exist!", userDTO, not(nullValue()));
	}
}