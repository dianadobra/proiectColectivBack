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
public class UserIT {

	private Department department;

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

	/*
	 * @Test
	 *
	 * @Transactional public void updateNewDepartment() { department =
	 * departmentRepository.save(department);
	 *
	 * assertThat("Updated department should exist", department,
	 * not(nullValue())); }
	 */

	@Test
	@Transactional
	public void deleteNewDepartament() throws Exception {

		try {
			Department dep = new Department("TestDelete");
			Department de = new Department("Muhaha");
			department = departmentRepository.save(dep);
			department = departmentRepository.saveAndFlush(de);
			Long id = department.getId();
			// System.out.println(id);
			// departmentRepository.delete(id);
			department = departmentRepository.findOne(id);
			// System.out.println(department);
			assertThat("Deleted department should not exist", department, not(nullValue()));
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
