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
import com.webcbg.eppione.companysettings.repository.GenericPersonRepository;
import com.webcbg.eppione.companysettings.repository.UserRepository;
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
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private GenericPersonRepository genericPersonRepository;

	
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
		
		flow.setDocuments(new ArrayList<>());
		for(Long id: flowDTO.getDocumentsIds()){
			flow.getDocuments().add(this.documentRepository.findOne(id));
		}

		Funding funding = this.fundingRepository.findOneById(flowDTO.getFundingTypeId());
		flow.setFunding(funding);
		
		flow.setActive(true);
		
		User superior = null;
		
		List<GenericPerson> genericPersons = new ArrayList<>();
		
		if (funding.getType().equals("Fara finantare")){
			GenericPerson genericPerson = new GenericPerson();
			genericPerson.setFlow(flow);
			genericPerson.setFunding(funding);
			genericPerson.setHasApproved(false);
			if (creator.getFunction().equals(Function.Student) || creator.getFunction().equals(Function.CadruDidactic)){	
				superior = this.userService.getByFunctionAndDepartmentId(Function.DirectorDepartament, creator.getDepartment().getId());
				genericPerson.setUser(superior);	
				genericPersons.add(genericPerson);
				this.genericPersonRepository.save(genericPerson);
				
			}
			if (creator.getFunction().equals(Function.AngajatExclusivInProiecte)){
				superior = this.userService.getByFunction(Function.Decan);
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				this.genericPersonRepository.save(genericPerson);
			}
			if (creator.getFunction().equals(Function.ProfesorPensionar)){
				superior = this.userService.getByFunction(Function.DirectorScoalaDoctorala);
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				this.genericPersonRepository.save(genericPerson);
			}
			if (creator.getFunction().equals(Function.PersonalAdministrativ)){
				superior = this.userService.getByFunction(Function.SefDirect);
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				this.genericPersonRepository.save(genericPerson);
			}
		} else if (flow.getFunding().getType().equals("Finantare din bugetul facultatii")){
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
		
		flow.setGenericPersons(new ArrayList<>());
		flow.getGenericPersons().addAll(genericPersons);
		
		this.flowRepository.save(flow);
		
		if (superior.getAwaitingFlows()==null){
			superior.setAwaitingFlows(new ArrayList<>());
		}
		superior.getAwaitingFlows().add(flow);
		userRepository.save(superior);

		return this.flowConveter.toDto(flow);
	}
	
	public List<Funding> getAllFundings(){
		return this.fundingRepository.findAll();
	}
	
	public List<FlowDTO> getGenericPersonFlows(Long userId){
		return this.flowConveter.toDtoList(this.userRepository.findOne(userId).getAwaitingFlows());
	}

	public List<FlowDTO> getUserFlow(Long userId) {
		return this.flowConveter.toDtoList(this.flowRepository.findAllByCreator_IdAndIsActive(userId, true));
	}
	
	public List<FlowDTO> getFinishedFlows(Long userId){
		return this.flowConveter.toDtoList(this.flowRepository.findAllByCreator_IdAndIsActive(userId, false));
	}
}
