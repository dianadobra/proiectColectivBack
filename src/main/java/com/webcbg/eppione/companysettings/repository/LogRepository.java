package com.webcbg.eppione.companysettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

	List<Log> findAll();

}
