package com.webcbg.eppione.companysettings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcbg.eppione.companysettings.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
