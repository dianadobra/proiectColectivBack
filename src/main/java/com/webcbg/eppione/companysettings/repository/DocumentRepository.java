package com.webcbg.eppione.companysettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcbg.eppione.companysettings.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findAllByAuthorId(Long authorId);
	
	List<Document> findAllByName(String name);

}
