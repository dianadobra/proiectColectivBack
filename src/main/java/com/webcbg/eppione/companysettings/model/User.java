package com.webcbg.eppione.companysettings.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "id")
@Entity
@ToString(exclude = "password")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	@JsonIgnore
	private String password;
	private Function function;
	private Long idSuperior;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_name", referencedColumnName = "name") })
	private Set<Role> roles = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "user_awaiting_flows", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "flow_id", referencedColumnName = "id") })
	private List<Flow> awaitingFlows = new ArrayList<>();
	
	
	public enum Function{
		DirectorDepartament, 
		Decan, 
		DirectorScoalaDoctorala, 
		SefDirect, 
		Rector, 
		DirectorGrant, 
		DirectorProiect, 
		DirectiaFinanciarContabila, 
		CMCS, 
		Programmer,
		Student,
		CadruDidactic,
		AngajatExclusivInProiecte,
		ProfesorPensionar,
		PersonalAdministrativ

	}

}
