package com.webcbg.eppione.companysettings.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webcbg.eppione.companysettings.rest.dto.UserDTO;
import com.webcbg.eppione.companysettings.rest.util.ErrorInfoFactory;
import com.webcbg.eppione.companysettings.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		final List<UserDTO> users = userService.getAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/department/{departmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getAllDepartmentUsers(@PathVariable final Long departmentId) {
		final List<UserDTO> users = userService.getAllByDepartment(departmentId);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getById(@PathVariable final Long userId) {
		final UserDTO user = userService.getById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody @Valid final UserDTO userDTO, final BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("User fields are incorrect", "user", result),
					HttpStatus.BAD_REQUEST);
		}
		final UserDTO user = userService.createUser(userDTO);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable final long id, @RequestBody @Valid final UserDTO userDTO,
			final BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("User fields are incorrect", "user", result),
					HttpStatus.BAD_REQUEST);
		}
		final UserDTO user = userService.updateUser(id, userDTO);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok(null);
	}

}
