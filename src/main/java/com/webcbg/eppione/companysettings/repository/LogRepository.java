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

	@Query(value = "select l.* from log l where (?1 is null or ?1=-1 or l.entity_type = ?1) and (?2 is null or ?2=-1 or l.action = ?2) and (?3 is null or ?3=-1 or l.user_id = ?3)", nativeQuery = true)
	List<Log> filterLogs(@Param("entity") Long entityType, @Param("action") Long actionType,
			@Param("userId") Long userId);

}
