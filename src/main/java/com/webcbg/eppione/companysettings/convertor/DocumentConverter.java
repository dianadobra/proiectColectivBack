package com.webcbg.eppione.companysettings.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.repository.UserRepository;
import com.webcbg.eppione.companysettings.rest.dto.DocumentDTO;

@Component
public class DocumentConverter {

	@Autowired
	private UserRepository userRepository;

	public Document toEntity(DocumentDTO documentDTO, Document document) {
		document.setAbstractInput(documentDTO.getAbstractInput());
		document.setAuthor(userRepository.findOne(documentDTO.getAuthorId()));
		document.setCreationDate(documentDTO.getCreationDate());
		document.setKeywords(documentDTO.getKeywords());
		document.setSigned(documentDTO.isSigned());
		document.setSignedBy(
				documentDTO.getSignedById() == null ? null : userRepository.findOne(documentDTO.getSignedById()));
		document.setUpdateDate(documentDTO.getUpdateDate());
		document.setVersion(documentDTO.getVersion());

		return document;
	}

	public DocumentDTO toDTO(final Document document) {
		final DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setAbstractInput(document.getAbstractInput());
		documentDTO.setAuthorId(document.getId());
		documentDTO.setCreationDate(document.getCreationDate());
		documentDTO.setKeywords(document.getKeywords());
		documentDTO.setSigned(document.isSigned());
		documentDTO.setSignedById(document.getSignedBy() == null ? null : document.getSignedBy().getId());
		documentDTO.setUpdateDate(document.getUpdateDate());
		documentDTO.setVersion(document.getVersion());

		return documentDTO;
	}

}
