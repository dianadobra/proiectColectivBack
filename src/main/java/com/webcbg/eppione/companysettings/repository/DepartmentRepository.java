package com.webcbg.eppione.companysettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	public Department findOneByName(String name);

}
