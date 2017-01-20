package com.webcbg.eppione.companysettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;

public interface LogRepository extends JpaRepository<Log, Long> {

	@Override
	List<Log> findAll();

	List<Log> findAllByUser_Id(Long userId);

	List<Log> findAllByEntityType(LogEntity entityType);

	List<Log> findAllByEntityTypeAndAction(LogEntity entityType, LogAction actionType);

	List<Log> findAllByEntityTypeAndActionAndUser_Id(LogEntity entityType, LogAction actionType, Long userId);

	@Query("Select l from Log l where (l.entityType=:entity) AND (l.action=:action) AND (l.user.id=:userId)")
	List<Log> filter(@Param("entity") LogEntity entityType, @Param("action") LogAction actionType,
			@Param("userId") Long userId);

}
