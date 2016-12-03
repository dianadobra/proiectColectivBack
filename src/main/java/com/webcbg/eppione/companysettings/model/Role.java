package com.webcbg.eppione.companysettings.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Data
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Role ADMIN = new Role("ROLE_ADMIN");
	public static Role MANAGER = new Role("ROLE_MANAGER");
	public static Role CONTRIBUTOR = new Role("ROLE_CONTRIBUTOR");
	public static Role READER = new Role("ROLE_READER");

	@NotNull
	@Size(min = 0, max = 50)
	@Id
	@Column(length = 50)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public static Set<Role> getRoleAsAuthorities(final Role authority) {
		final Set<Role> authorities = new HashSet<>(1);
		authorities.add(authority);
		return authorities;
	}

	public static Set<Role> getRoleAsAuthorities(final String authorityName) {
		final Role authority = new Role();
		authority.setName(authorityName);
		return getRoleAsAuthorities(authority);
	}

}
