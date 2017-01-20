package com.webcbg.eppione.companysettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;

public interface LogRepository extends JpaRepository<Log, Long> {

	@Override
	List<Log> findAll();

	List<Log> findAllByUser_Id(Long userId);

	List<Log> findAllByEntityType(LogEntity entityType);

	List<Log> findAllByEntityTypeAndAction(LogEntity entityType, LogAction actionType);

}
