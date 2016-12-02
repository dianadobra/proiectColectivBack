package com.webcbg.eppione.companysettings.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webcbg.eppione.companysettings.rest.dto.DepartmentDTO;
import com.webcbg.eppione.companysettings.rest.util.ErrorInfoFactory;
import com.webcbg.eppione.companysettings.service.DepartmentService;

@RestController
@RequestMapping("api/departments")
public class DepartmentResource {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	public ResponseEntity<List<DepartmentDTO>> getDepartments() {
		final List<DepartmentDTO> departments = departmentService.getAll();
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable final Long id) {
		final DepartmentDTO department = departmentService.getById(id);
		return Optional.ofNullable(department).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createDepartment(@RequestBody @Valid final DepartmentDTO departmentDTO,
			final BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("Department fields are incorrect", "department", result),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(departmentService.createDepartment(departmentDTO));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDepartment(@PathVariable final Long id,
			@RequestBody @Valid final DepartmentDTO departmentDTO, final BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("Department fields are incorrect", "department", result),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(departmentService.updateDepartment(departmentDTO, id), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteDepartment(@PathVariable final Long id) {
		departmentService.deleteDepartment(id);
	}

}