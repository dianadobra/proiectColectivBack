package com.webcbg.eppione.companysettings.convertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.rest.dto.LogDTO;

@Component
public class LogConverter {

	public LogDTO toDTO(final Log log) {
		final LogDTO logDTO = new LogDTO();
		Date date = log.getDate();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String finalDate = df.format(date);
		logDTO.setDate(finalDate);
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
