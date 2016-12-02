package com.webcbg.eppione.companysettings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcbg.eppione.companysettings.convertor.DepartmentConvertor;
import com.webcbg.eppione.companysettings.model.Department;
import com.webcbg.eppione.companysettings.repository.DepartmentRepository;
import com.webcbg.eppione.companysettings.rest.dto.DepartmentDTO;
import com.webcbg.eppione.companysettings.service.errors.ResourceAlreadyExistException;
import com.webcbg.eppione.companysettings.service.errors.ResourceNotFoundException;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private DepartmentConvertor departmentConvertor;

	public List<DepartmentDTO> getAll() {
		return departmentConvertor.toDtoList(departmentRepository.findAll());
	}

	public DepartmentDTO getById(final Long departmentId) {
		return departmentConvertor.toDTO(departmentRepository.findOne(departmentId));
	}

	public DepartmentDTO createDepartment(final DepartmentDTO departmentDTO) {
		if (departmentRepository.findOneByName(departmentDTO.getName()) != null) {
			throw new ResourceAlreadyExistException("Department name already exists!");
		}
		Department department = new Department();
		department = departmentConvertor.toEntity(departmentDTO, department);
		return departmentConvertor.toDTO(departmentRepository.save(department));

	}

	public DepartmentDTO updateDepartment(final DepartmentDTO departmentDTO, final Long id) {
		Department department = departmentRepository.findOne(id);
		if (department != null) {
			department = departmentConvertor.toEntity(departmentDTO, department);
			return departmentConvertor.toDTO(departmentRepository.save(department));
		} else {
			throw new ResourceNotFoundException(
					String.format("Department with id %s not found", departmentDTO.getId()));
		}
	}

	public void deleteDepartment(final Long departmentId) {
		departmentRepository.delete(departmentId);
	}
}
