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
import com.webcbg.eppione.companysettings.model.Flow.ApprovalStatus;
import com.webcbg.eppione.companysettings.model.Funding;
import com.webcbg.eppione.companysettings.model.GenericPerson;
import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;
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

	@Autowired
	private LogService logService;

	public FlowDTO createFlow(FlowDTO flowDTO) {
		Flow flow = new Flow();
		flow = flowConveter.toEntity(flowDTO, flow);

		flow.setCreationTime(new Date());

		User creator = userService.getUser(flowDTO.getCreatorId());
		flow.setCreator(creator);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, flowDTO.getReviewTime());
		flow.setDeadline(cal.getTime());

		flow.setDocuments(new ArrayList<>());
		for (Long id : flowDTO.getDocumentsIds()) {
			flow.getDocuments().add(documentRepository.findOne(id));
		}

		Funding funding = fundingRepository.findOneById(flowDTO.getFundingTypeId());
		flow.setFunding(funding);

		flow.setActive(true);
		flow.setApprovalStatus(ApprovalStatus.Unapproved);
		flow.setComments(new ArrayList<String>());

		List<GenericPerson> genericPersons = this.getGenericPersons(flow, funding, creator);

		flow.setGenericPersons(new ArrayList<>());
		flow.getGenericPersons().addAll(genericPersons);

		flowRepository.save(flow);

		Log log = new Log();
		log.setAction(LogAction.Create);
		Date dateObj = new Date();
		log.setDate(dateObj);
		log.setDescription("Flow " + flow.getName() + " created. ");
		log.setEntityId(flow.getId());
		log.setEntityType(LogEntity.Flow);
		log.setUser(flow.getCreator());

		logService.createLog(log);

		return flowConveter.toDto(flow);
	}

	public List<GenericPerson> getGenericPersons(Flow flow, Funding funding, User creator) {
		List<GenericPerson> genericPersons = new ArrayList<>();

		User superior = null;

		if (funding.getType().equals("Fara finantare")) {
			GenericPerson genericPerson = new GenericPerson();
			genericPerson.setFlow(flow);
			genericPerson.setFunding(funding);
			genericPerson.setHasApproved(false);
			if (creator.getFunction().equals(Function.Student)
					|| creator.getFunction().equals(Function.CadruDidactic)) {
				superior = userService.getByFunctionAndDepartmentId(Function.DirectorDepartament,
						creator.getDepartment().getId());
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				genericPersonRepository.save(genericPerson);

			}
			if (creator.getFunction().equals(Function.AngajatExclusivInProiecte)) {
				superior = userService.getByFunction(Function.Decan);
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				genericPersonRepository.save(genericPerson);
			}
			if (creator.getFunction().equals(Function.ProfesorPensionar)) {
				superior = userService.getByFunction(Function.DirectorScoalaDoctorala);
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				genericPersonRepository.save(genericPerson);
			}
			if (creator.getFunction().equals(Function.PersonalAdministrativ)) {
				superior = userService.getByFunction(Function.SefDirect);
				genericPerson.setUser(superior);
				genericPersons.add(genericPerson);
				genericPersonRepository.save(genericPerson);
			}
		} else if (flow.getFunding().getType().equals("Finantare din bugetul facultatii")) {
			GenericPerson genericPerson = new GenericPerson();
			genericPerson.setFlow(flow);
			genericPerson.setFunding(funding);
			genericPerson.setHasApproved(false);
			genericPerson.setUser(userService.getByFunction(Function.Decan));
			genericPersons.add(genericPerson);
			// add list with all from department directia financiar contabila
			genericPerson.setUser(userService.getByFunction(Function.DirectiaFinanciarContabila));
			genericPersons.add(genericPerson);

		}

		if (superior.getAwaitingFlows() == null) {
			superior.setAwaitingFlows(new ArrayList<>());
		}
		superior.getAwaitingFlows().add(flow);
		userRepository.save(superior);

		return genericPersons;

	}

	public List<Funding> getAllFundings() {
		return fundingRepository.findAll();
	}

	public List<FlowDTO> getGenericPersonFlows(Long userId) {
		return flowConveter.toDtoList(userRepository.findOne(userId).getAwaitingFlows());
	}

	public List<FlowDTO> getUserFlow(Long userId) {
		return flowConveter.toDtoList(flowRepository.findAllByCreator_IdAndIsActive(userId, true));
	}

	public List<FlowDTO> getFinishedFlows(Long userId) {
		return flowConveter.toDtoList(flowRepository.findAllByCreator_IdAndIsActive(userId, false));
	}

	public List<FlowDTO> getAllFinishedFlows() {
		return flowConveter.toDtoList(flowRepository.findAllByIsActive(false));
	}

	public FlowDTO changeStatus(Long flowId, Long userId, ApprovalStatus status, String comment) {

		if (status.equals(ApprovalStatus.Approved)) {
			Flow flow = flowRepository.findOne(flowId);
			User user = userRepository.findOne(userId);

			user.getAwaitingFlows().remove(flow);
			flow.setApprovalStatus(ApprovalStatus.Approved);
			GenericPerson genericPerson = flow.getGenericPersons().get(0);
			flow.getGenericPersons().remove(0);
			genericPersonRepository.delete(genericPerson);
			flow.getComments().add(comment);

			if (flow.getGenericPersons().isEmpty()) {
				flow.setActive(false);
			} else {
				GenericPerson next = flow.getGenericPersons().get(0);
				User superior = userRepository.findOne(next.getId());
				superior.getAwaitingFlows().add(flow);
				genericPersonRepository.save(next);
				userRepository.save(superior);
			}

			flowRepository.save(flow);
			userRepository.save(user);

			Log log = new Log();
			log.setAction(LogAction.ChangeStatus);
			Date dateObj = new Date();
			log.setDate(dateObj);
			log.setDescription("Flow " + flow.getName() + " status changed to: " + flow.getApprovalStatus());
			log.setEntityId(flow.getId());
			log.setEntityType(LogEntity.Flow);
			log.setUser(flow.getCreator());

			logService.createLog(log);

			return flowConveter.toDto(flow);
		} else if (status.equals(ApprovalStatus.Unapproved)) {
			Flow flow = flowRepository.findOne(flowId);
			User user = userRepository.findOne(userId);

			GenericPerson genericPerson = flow.getGenericPersons().get(0);
			genericPersonRepository.delete(genericPerson);

			user.getAwaitingFlows().remove(flow);
			flow.setApprovalStatus(ApprovalStatus.Unapproved);
			flow.getGenericPersons().removeAll(flow.getGenericPersons());
			flow.setActive(false);
			flow.getComments().add(comment);

			flowRepository.save(flow);
			userRepository.save(user);

			Log log = new Log();
			log.setAction(LogAction.ChangeStatus);
			Date dateObj = new Date();
			log.setDate(dateObj);
			log.setDescription("Flow " + flow.getName() + " status changed to: " + flow.getApprovalStatus());
			log.setEntityId(flow.getId());
			log.setEntityType(LogEntity.Flow);
			log.setUser(flow.getCreator());

			logService.createLog(log);

			return flowConveter.toDto(flow);
		} else {
			Flow flow = flowRepository.findOne(flowId);
			User user = userRepository.findOne(userId);

			User creator = flow.getCreator();
			creator.getAwaitingFlows().add(flow);
			userRepository.save(creator);

			user.getAwaitingFlows().remove(flow);
			flow.setApprovalStatus(ApprovalStatus.NeedsRevision);
			flow.setActive(true);
			flow.getComments().add(comment);
			flow.setGenericPersons(new ArrayList<>());

			GenericPerson genericPerson = new GenericPerson();
			genericPerson.setFlow(flow);
			genericPerson.setFunding(flow.getFunding());
			genericPerson.setHasApproved(false);
			genericPerson.setUser(creator);
			genericPersonRepository.save(genericPerson);

			flow.setGenericPersons(this.getGenericPersons(flow, flow.getFunding(), creator));

			flowRepository.save(flow);
			userRepository.save(user);

			Log log = new Log();
			log.setAction(LogAction.ChangeStatus);
			Date dateObj = new Date();
			log.setDate(dateObj);
			log.setDescription("Flow " + flow.getName() + " status changed to: " + flow.getApprovalStatus());
			log.setEntityId(flow.getId());
			log.setEntityType(LogEntity.Flow);
			log.setUser(flow.getCreator());

			logService.createLog(log);

			return flowConveter.toDto(flow);
		}

	}
}
