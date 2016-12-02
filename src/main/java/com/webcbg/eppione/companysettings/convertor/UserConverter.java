package com.webcbg.eppione.companysettings.convertor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Role;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.DepartmentRepository;
import com.webcbg.eppione.companysettings.rest.dto.UserDTO;

@Component
public class UserConverter {

	@Autowired
	private DepartmentRepository departmentRepository;

	public User toEntity(final UserDTO userDTO, final User user) {
		final Set<Role> role = new HashSet<>();
		role.add(userDTO.getRole());
		user.setAuthorities(role);
		user.setDepartment(departmentRepository.findOne(userDTO.getDepartmentId()));
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setFunction(userDTO.getFunction());
		user.setIdSuperior(userDTO.getIdSuperior());
		user.setLastName(userDTO.getLastName());
		user.setUsername(userDTO.getUsername());

		return user;
	}

	public UserDTO toDTO(final User user) {
		final UserDTO userDTO = new UserDTO();
		final Set<Role> roles = user.getAuthorities();
		final Iterator<Role> it = roles.iterator();
		userDTO.setRole(it.next());
		userDTO.setDepartmentId(user.getDepartment().getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setFunction(user.getFunction());
		userDTO.setIdSuperior(user.getIdSuperior());
		userDTO.setLastName(user.getLastName());
		userDTO.setUsername(user.getUsername());

		return userDTO;
	}

	public List<UserDTO> toDtoList(final List<User> list) {
		return list.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
