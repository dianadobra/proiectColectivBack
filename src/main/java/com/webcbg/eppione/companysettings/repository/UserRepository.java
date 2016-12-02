package com.webcbg.eppione.companysettings.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webcbg.eppione.companysettings.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findOneByUsername(String username);

	public Optional<User> findOneByEmail(String email);

	public List<User> findAllByDepartmentId(Long departmentId);
}
