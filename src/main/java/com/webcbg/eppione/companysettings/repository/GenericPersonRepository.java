package com.webcbg.eppione.companysettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.GenericPerson;

public interface GenericPersonRepository extends JpaRepository<GenericPerson, Long>{
	
}
