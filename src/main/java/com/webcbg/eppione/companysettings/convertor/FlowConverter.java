package com.webcbg.eppione.companysettings.convertor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.model.Flow;
import com.webcbg.eppione.companysettings.rest.dto.FlowDTO;

@Component
public class FlowConverter {
	
	@Autowired
	private DocumentConverter documentConverter;
	
	public Flow toEntity(FlowDTO flowDTO, Flow flow) {
		flow.setName(flowDTO.getName());
		flow.setReviewTime(flowDTO.getReviewTime());
		flow.setApprovalStatus(flowDTO.getStatus()==null ? flow.getApprovalStatus() : flowDTO.getStatus());
		return flow;
	}
	
	public FlowDTO toDto(Flow flow){
		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setId(flow.getId());
		flowDTO.setActive(flow.isActive());
		flowDTO.setCreator(flow.getCreator().getFirstName()+" "+flow.getCreator().getLastName());
		flowDTO.setDocuments(new ArrayList<>());
		for (Document doc: flow.getDocuments()){
			flowDTO.getDocuments().add(this.documentConverter.toDTO(doc));
		}
		flowDTO.setFundingType(flow.getFunding().getType());
		flowDTO.setName(flow.getName());
		flowDTO.setStatus(flow.getApprovalStatus());
		flowDTO.setComments(flow.getComments());
		
		return flowDTO;
	}
	
	public List<FlowDTO> toDtoList(final List<Flow> list) {
		return list.stream().map(this::toDto).collect(Collectors.toList());
	}
}