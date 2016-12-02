package com.webcbg.eppione.companysettings.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Department;
import com.webcbg.eppione.companysettings.rest.dto.DepartmentDTO;

@Component
public class DepartmentConvertor {

	public Department toEntity(final DepartmentDTO departmentDTO, final Department department) {
		department.setName(departmentDTO.getName());

		return department;
	}

	public DepartmentDTO toDTO(final Department department) {
		final DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setId(department.getId());
		departmentDTO.setName(department.getName());

		return departmentDTO;
	}

	public List<DepartmentDTO> toDtoList(final List<Department> list) {
		return list.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
