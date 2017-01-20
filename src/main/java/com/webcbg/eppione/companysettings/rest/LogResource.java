package com.webcbg.eppione.companysettings.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;
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

	@RequestMapping(value = "/userId/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	public ResponseEntity<List<LogDTO>> getLogsByUserId(@PathVariable final Long userId) {
		final List<LogDTO> logs = logService.getAllByUserId(userId);
		return new ResponseEntity<>(logs, HttpStatus.OK);
	}

	@RequestMapping(value = "/logEntity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	public ResponseEntity<List<LogDTO>> getLogsByEntityType(
			@RequestParam(name = "logEntity", required = true) final LogEntity logEntity) {
		final List<LogDTO> logs = logService.getAllByLogEntity(logEntity);
		return new ResponseEntity<>(logs, HttpStatus.OK);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	public ResponseEntity<List<LogDTO>> getLogsByEntityTypeAndLogAction(
			@RequestParam(name = "logEntity", required = false) final LogEntity logEntity,
			@RequestParam(name = "logAction", required = false) final LogAction logAction,
			@RequestParam(name = "userId", required = false) final Long userId) {

		final List<LogDTO> logs = logService.filterLogs(logEntity, logAction, userId);
		return new ResponseEntity<>(logs, HttpStatus.OK);
	}

}
