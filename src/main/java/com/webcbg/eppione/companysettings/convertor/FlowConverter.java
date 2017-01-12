package com.webcbg.eppione.companysettings.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.model.Flow;
import com.webcbg.eppione.companysettings.rest.dto.FlowDTO;

@Component
public class FlowConverter {
	
	public Flow toEntity(FlowDTO flowDTO, Flow flow) {
		flow.setName(flowDTO.getName());
		flow.setReviewTime(flowDTO.getReviewTime());
		return flow;
	}
	
	public FlowDTO toDto(Flow flow){
		FlowDTO flowDTO = new FlowDTO();
		flowDTO.setActive(flow.isActive());
		flowDTO.setCreator(flow.getCreator().getFirstName()+" "+flow.getCreator().getLastName());
		for (Document doc: flow.getDocuments()){
			String url = "http://localhost:8080/eppione/api/documents/" + doc.getId();
			flowDTO.getDocumentsUrls().add(url);
		}
		flowDTO.setFundingType(flow.getFunding().getType());
		flowDTO.setName(flow.getName());
		
		return flowDTO;
	}
	
	public List<FlowDTO> toDtoList(final List<Flow> list) {
		return list.stream().map(this::toDto).collect(Collectors.toList());
	}
}