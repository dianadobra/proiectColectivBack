package com.webcbg.eppione.companysettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {
	
	Funding findOneByType(String type);

}
