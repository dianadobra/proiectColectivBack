package com.webcbg.eppione.companysettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findAllByAuthorIdOrderByGuidAsc(Long authorId);

	List<Document> findAllByName(String name);

	List<Document> findAllByDocumentState(DocumentStatus docState);

	List<Document> findAllByDocumentStateAndPreparedForDelete(DocumentStatus docState, boolean prepared);
	
	List<Document> findAllByGuidOrderByVersionDesc(int guid);

}
