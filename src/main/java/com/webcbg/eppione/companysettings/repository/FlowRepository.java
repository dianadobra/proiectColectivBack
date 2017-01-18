package com.webcbg.eppione.companysettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.Flow;

public interface FlowRepository extends JpaRepository<Flow, Long>{
	
	public List<Flow> findAllByCreator_Id(Long userId);
	
	public List<Flow> findAllByCreator_IdAndIsActive(Long userId, boolean isActive);

}
