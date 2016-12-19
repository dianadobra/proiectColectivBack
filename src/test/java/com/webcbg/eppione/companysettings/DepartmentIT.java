package com.webcbg.eppione.companysettings;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.webcbg.eppione.EppioneApplication;
import com.webcbg.eppione.companysettings.convertor.DepartmentConvertor;
import com.webcbg.eppione.companysettings.model.Department;
import com.webcbg.eppione.companysettings.repository.DepartmentRepository;
import com.webcbg.eppione.companysettings.rest.dto.DepartmentDTO;
import com.webcbg.eppione.companysettings.service.DepartmentService;
import com.webcbg.eppione.companysettings.service.errors.ResourceAlreadyExistException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = EppioneApplication.class)
public class DepartmentIT {

	private Department department;
	private DepartmentDTO departmentDTO;
	private DepartmentDTO departmentDTOO;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	DepartmentConvertor departmentConverter;

	@Before
	public void setup() {
		department = new Department("unul nou");
	}

	@After
	public void cleanup() {
		departmentRepository.delete(department);
	}

	@Test
	@Transactional
	public void createNewDepartment() {
		Department dep = new Department("TestCreate");
		department = departmentRepository.save(dep);
		Long id = department.getId();
		department = departmentRepository.findOne(id);
		assertThat("Created department should exist!", department, not(nullValue()));
	}

	@Test
	@Transactional
	public void createTheDepartment() {
		DepartmentDTO d = new DepartmentDTO();
		d.setName("NewTest");
		departmentDTO = departmentService.createDepartment(d);
		assertThat("Created departmentDTO should exist!", departmentDTO, not(nullValue()));
	}

	@Test
	@Transactional
	public void updateTheDepartment() {
		DepartmentDTO d = new DepartmentDTO();
		d.setName("NewTest");
		departmentDTO = departmentService.createDepartment(d);
		String s = departmentDTO.getName();
		// System.out.println(s);
		DepartmentDTO dd = new DepartmentDTO();
		dd.setName("Update");
		departmentDTOO = departmentService.createDepartment(dd);
		departmentDTO = departmentService.updateDepartment(departmentDTOO, departmentDTO.getId());
		// System.out.println(departmentDTO.getName());
		String ss = departmentDTO.getName();
		assertThat("Update should change the name!", s, not(ss));
	}

	@Test
	@Transactional
	public void deleteTheDepartament() throws Exception {

		DepartmentDTO d = new DepartmentDTO();
		d.setName("DeleteTest");
		departmentDTOO = departmentService.createDepartment(d);
		d = departmentService.getById(departmentDTOO.getId());
		departmentService.deleteDepartment(d.getId());
		// d = departmentService.getById(departmentDTOO.getId());
		try {
			assertThat("Deleted departmentDTO should not exist!", departmentService.getById(departmentDTOO.getId()),
					(nullValue()));
		} catch (Exception e) {
			System.out.println("It's deleted!");
		}
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void whenCreateDepartmentThatAlreadyExistInCompany_AnExceptionIsThrown() {
		final DepartmentDTO oldDep = departmentConverter.toDTO(department);
		final DepartmentDTO newDep = departmentService.createDepartment(oldDep);
		assertThat("Created department should exist", newDep, not(nullValue()));

		final Department newdDepartment = new Department("unul nou");
		final DepartmentDTO newdDepartmentDTO = departmentConverter.toDTO(newdDepartment);
		departmentService.createDepartment(newdDepartmentDTO);
	}

}
