package com.webcbg.eppione.companysettings.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.rest.dto.LogDTO;

@Component
public class LogConverter {

	public LogDTO toDTO(final Log log) {
		final LogDTO logDTO = new LogDTO();
		logDTO.setDate(log.getDate());
		logDTO.setAction(log.getAction());
		logDTO.setDescription(log.getDescription());
		logDTO.setEntityId(log.getEntityId());
		logDTO.setEntityType(log.getEntityType());
		String userName = log.getUser().getFirstName() + " " + log.getUser().getLastName();
		logDTO.setUserName(userName);
		return logDTO;
	}

	public List<LogDTO> toDTOList(final List<Log> list) {
		return list.stream().map(this::toDTO).collect(Collectors.toList());
	}
}
