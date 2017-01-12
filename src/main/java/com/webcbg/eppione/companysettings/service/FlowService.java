package com.webcbg.eppione.companysettings.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcbg.eppione.companysettings.convertor.FlowConverter;
import com.webcbg.eppione.companysettings.model.Flow;
import com.webcbg.eppione.companysettings.model.Funding;
import com.webcbg.eppione.companysettings.model.GenericPerson;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.model.User.Function;
import com.webcbg.eppione.companysettings.repository.DocumentRepository;
import com.webcbg.eppione.companysettings.repository.FlowRepository;
import com.webcbg.eppione.companysettings.repository.FundingRepository;
import com.webcbg.eppione.companysettings.rest.dto.FlowDTO;

@Service
@Transactional
public class FlowService {
	
	@Autowired
	private FlowRepository flowRepository;
	
	@Autowired
	private FlowConverter flowConveter;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired 
	private FundingRepository fundingRepository;
	
	public FlowDTO createFlow(FlowDTO flowDTO){
		Flow flow = new Flow();
		flow = this.flowConveter.toEntity(flowDTO, flow);
		
		flow.setCreationTime(new Date());
		
		User creator = this.userService.getUser(flowDTO.getCreatorId());
		flow.setCreator(creator);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, flowDTO.getReviewTime());
		flow.setDeadline(cal.getTime());
		
		for(Long id: flowDTO.getDocumentsIds()){
			flow.getDocuments().add(this.documentRepository.findOne(id));
		}
		Funding funding = this.fundingRepository.findOneByType(flowDTO.getFundingType());
		flow.setFunding(funding);
		
		List<GenericPerson> genericPersons = new ArrayList<>();
		
		if (flowDTO.getFundingType().equals("Fara Finantare")){
			GenericPerson genericPerson = new GenericPerson();
			genericPerson.setFlow(flow);
			genericPerson.setFunding(funding);
			genericPerson.setHasApproved(false);
			if (creator.getFunction().equals(Function.Student) || creator.getFunction().equals(Function.CadruDidactic)){	
				genericPerson.setUser(this.userService.getByFunctionAndDepartmentId(Function.DirectorDepartament, creator.getDepartment().getId()));	
				genericPersons.add(genericPerson);
			}
			if (creator.getFunction().equals(Function.AngajatExclusivInProiecte)){
				genericPerson.setUser(this.userService.getByFunction(Function.Decan));
				genericPersons.add(genericPerson);
			}
			if (creator.getFunction().equals(Function.ProfesorPensionar)){
				genericPerson.setUser(this.userService.getByFunction(Function.DirectorScoalaDoctorala));
				genericPersons.add(genericPerson);
			}
			if (creator.getFunction().equals(Function.PersonalAdministrativ)){
				genericPerson.setUser(this.userService.getByFunction(Function.SefDirect));
				genericPersons.add(genericPerson);
			}
		} else if (flowDTO.getFundingType().equals("Finantare din bugetul facultatii")){
			GenericPerson genericPerson = new GenericPerson();
			genericPerson.setFlow(flow);
			genericPerson.setFunding(funding);
			genericPerson.setHasApproved(false);
			genericPerson.setUser(this.userService.getByFunction(Function.Decan));	
			genericPersons.add(genericPerson);
			//add list with all from department directia financiar contabila
			genericPerson.setUser(this.userService.getByFunction(Function.DirectiaFinanciarContabila));	
			genericPersons.add(genericPerson);
		
		
		}

		return flowDTO;
	}
}
