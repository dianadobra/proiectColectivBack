package com.webcbg.eppione.companysettings.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webcbg.eppione.companysettings.rest.dto.LogDTO;
import com.webcbg.eppione.companysettings.service.LogService;

@RestController
@RequestMapping("api/logs")
public class LogResource {
	@Autowired
	private LogService logService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	public ResponseEntity<List<LogDTO>> getLogs() {
		final List<LogDTO> logs = logService.getAll();
		return new ResponseEntity<>(logs, HttpStatus.OK);
	}

}
