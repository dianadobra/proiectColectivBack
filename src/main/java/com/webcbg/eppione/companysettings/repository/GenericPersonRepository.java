package com.webcbg.eppione.companysettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webcbg.eppione.companysettings.model.GenericPerson;

public interface GenericPersonRepository extends JpaRepository<GenericPerson, Long>{ 
	
	@Query("delete from GenericPerson g where g.flow.id=:flowId")
	void deleteByFlowId(@Param("flowId") Long flowId);
	
}
