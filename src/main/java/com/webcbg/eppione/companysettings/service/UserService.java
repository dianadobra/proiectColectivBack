package com.webcbg.eppione.companysettings.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webcbg.eppione.companysettings.convertor.UserConverter;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.UserRepository;
import com.webcbg.eppione.companysettings.rest.dto.UserDTO;
import com.webcbg.eppione.companysettings.service.errors.ResourceAlreadyExistException;
import com.webcbg.eppione.companysettings.service.errors.ResourceNotFoundException;
import com.webcbg.eppione.security.SecurityUtils;

@Service
@Transactional
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Gets the current logged in user
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public User getCurrentUser() {
		return userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin()).get();
	}

	public UserDTO createUser(final UserDTO userDTO) {
		User user = new User();
		if (userRepository.findOneByUsername(userDTO.getUsername()).isPresent()) {
			throw new ResourceAlreadyExistException("Name alreay exists!");
		}
		user = userConverter.toEntity(userDTO, user);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		return userConverter.toDTO(userRepository.save(user));

	}

	public UserDTO updateUser(final long id, final UserDTO userDTO) {

		User user = userRepository.findOne(id);
		if (user == null) {
			throw new ResourceNotFoundException("User not found!");
		}
		user = userConverter.toEntity(userDTO, user);
		return userConverter.toDTO(userRepository.saveAndFlush(user));
	}

	public void deleteUser(final long id) {
		final User user = userRepository.findOne(id);
		if (user == null) {
			throw new ResourceNotFoundException("User not found!");
		}
		userRepository.delete(id);
	}

	public void changePassword(final String password) {
		userRepository.findOneByUsername(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
			u.setPassword(password);
			userRepository.save(u);
			log.debug("Changed password for User: {}", u);
		});
	}

	public List<UserDTO> getAllByDepartment(final Long departmentId) {
		final List<User> users = userRepository.findAllByDepartmentId(departmentId);
		return userConverter.toDtoList(users);
	}

	public List<UserDTO> getAll() {
		return userConverter.toDtoList(userRepository.findAll());
	}

	public UserDTO getById(final Long userId) {
		return userConverter.toDTO(userRepository.findOne(userId));
	}

}
