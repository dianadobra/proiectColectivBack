package com.webcbg.eppione.companysettings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcbg.eppione.companysettings.convertor.LogConverter;
import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;
import com.webcbg.eppione.companysettings.repository.LogRepository;
import com.webcbg.eppione.companysettings.rest.dto.LogDTO;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	@Autowired
	private LogConverter logConverter;

	public LogDTO createLog(final Log log) {
		return logConverter.toDTO(logRepository.save(log));
	}

	public List<LogDTO> getAll() {
		return logConverter.toDTOList(logRepository.findAll());
	}

	public List<LogDTO> getAllByUserId(long userId) {
		return logConverter.toDTOList(logRepository.findAllByUser_Id(userId));
	}

	public List<LogDTO> getAllByLogEntity(LogEntity logEntity) {
		return logConverter.toDTOList(logRepository.findAllByEntityType(logEntity));
	}

	public List<LogDTO> getAllByLogEntityAndActionType(LogEntity logEntity, LogAction actionType) {
		return logConverter.toDTOList(logRepository.findAllByEntityTypeAndAction(logEntity, actionType));
	}

	public List<LogDTO> filterLogs(LogEntity logEntity, LogAction actionType, Long userId) {
		long entity = -1;
		long action = -1;
		if (logEntity != null && actionType != null) {
			entity = logEntity.ordinal();
			action = actionType.ordinal();
		} else if (logEntity != null) {
			entity = logEntity.ordinal();
		} else if (actionType != null) {
			action = actionType.ordinal();
		}
		return logConverter.toDTOList(logRepository.filterLogs(entity, action, userId));
	}
}
