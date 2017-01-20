package com.webcbg.eppione.companysettings.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webcbg.eppione.companysettings.model.Flow.ApprovalStatus;
import com.webcbg.eppione.companysettings.model.Funding;
import com.webcbg.eppione.companysettings.rest.dto.FlowDTO;
import com.webcbg.eppione.companysettings.rest.util.ErrorInfoFactory;
import com.webcbg.eppione.companysettings.service.FlowService;

@RestController
@RequestMapping("/api/flow")
public class FlowResource {
	
	@Autowired
	private FlowService flowService;
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody @Valid final FlowDTO flowDTO, final BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("Flow fields are incorrect", "user", result),
					HttpStatus.BAD_REQUEST);
		}
		FlowDTO flow = this.flowService.createFlow(flowDTO);
		return new ResponseEntity<>(flow, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/funding", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Funding>> getAllFundings() {
		final List<Funding> fundings = this.flowService.getAllFundings();
		return new ResponseEntity<>(fundings, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/genericPerson/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlowDTO>> getFlowsForGenericPerson(@PathVariable final Long userId) {
		final List<FlowDTO> flows = this.flowService.getGenericPersonFlows(userId);
		return new ResponseEntity<>(flows, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlowDTO>> getUserFlows(@PathVariable final Long userId) {
		final List<FlowDTO> flows = this.flowService.getUserFlow(userId);
		return new ResponseEntity<>(flows, HttpStatus.OK);
	}

	@RequestMapping(value = "blocked/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlowDTO>> getFinishedFlows(@PathVariable final Long userId) {
		final List<FlowDTO> flows = this.flowService.getFinishedFlows(userId);
		return new ResponseEntity<>(flows, HttpStatus.OK);
	}
	
	@RequestMapping(value = "blocked", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlowDTO>> getAllFinishedFlows() {
		final List<FlowDTO> flows = this.flowService.getAllFinishedFlows();
		return new ResponseEntity<>(flows, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{flowId}/user/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeStatus(
			@PathVariable final long userId, 
			@PathVariable final long flowId, 
			@RequestParam(name="status") ApprovalStatus status, 
			@RequestParam(name="comment", required=false) String comment) {
		
		final FlowDTO flow = flowService.changeStatus(flowId, userId, status, comment);
		return new ResponseEntity<>(flow, HttpStatus.OK);

	}
}
