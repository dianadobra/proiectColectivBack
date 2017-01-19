package com.webcbg.eppione.companysettings.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.rest.dto.LogDTO;

@Component
public class LogConverter {

	public Log toEntity(final LogDTO logDTO, final Log log) {
		log.setDate(logDTO.getDate());
		log.setAction(logDTO.getAction());
		log.setDescription(logDTO.getDescription());
		log.setEntityId(logDTO.getEntityId());
		log.setUser(logDTO.getUser());
		return log;
	}

	public LogDTO toDTO(final Log log) {
		final LogDTO logDTO = new LogDTO();
		logDTO.setDate(log.getDate());
		logDTO.setAction(log.getAction());
		logDTO.setDescription(log.getDescription());
		logDTO.setEntityId(log.getEntityId());
		logDTO.setUser(log.getUser());
		return logDTO;
	}

	public List<LogDTO> toDTOList(final List<Log> list) {
		return list.stream().map(this::toDTO).collect(Collectors.toList());
	}
}
