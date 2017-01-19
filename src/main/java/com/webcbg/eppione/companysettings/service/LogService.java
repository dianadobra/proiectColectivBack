package com.webcbg.eppione.companysettings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcbg.eppione.companysettings.convertor.LogConverter;
import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.repository.LogRepository;
import com.webcbg.eppione.companysettings.rest.dto.LogDTO;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	@Autowired
	private LogConverter logConverter;

	public LogDTO createLog(final LogDTO logDTO) {
		Log log = new Log();
		log = logConverter.toEntity(logDTO, log);
		return logConverter.toDTO(logRepository.save(log));
	}

	public List<LogDTO> getAll() {
		return logConverter.toDTOList(logRepository.findAll());
	}
}
