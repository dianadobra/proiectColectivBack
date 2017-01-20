package com.webcbg.eppione.companysettings.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.rest.dto.DocumentDTO;

@Component
public class DocumentConverter {

	public Document toEntity(DocumentDTO documentDTO, Document document) {
		document.setAbstractInput(documentDTO.getAbstractInput());
		document.setKeywords(documentDTO.getKeywords());
		if (documentDTO.getSigned()!=null){
			document.setSigned(documentDTO.getSigned());
		}

		return document;
	}

	public DocumentDTO toDTO(final Document document) {
		final DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setId(document.getId());
		documentDTO.setAbstractInput(document.getAbstractInput());
		documentDTO.setKeywords(document.getKeywords());
		documentDTO.setSigned(document.isSigned());
		documentDTO.setName(document.getName());
		documentDTO.setCreationDate(document.getCreationDate());
		documentDTO.setUpdateDate(document.getUpdateDate());
		documentDTO.setDocumentState(document.getDocumentState());
		documentDTO.setVersion(document.getVersion());
		documentDTO.setUsername(document.getAuthor().getFirstName() + " " + document.getAuthor().getLastName());
		documentDTO.setSignedByName(document.getSignedBy() == null ? null
				: document.getSignedBy().getFirstName() + " " + document.getSignedBy().getLastName());
		documentDTO.setGuid(document.getGuid());
		documentDTO.setDownloadUrl("http://localhost:8080/eppione/api/documents/" + document.getId());
		
		return documentDTO;
	}

	public List<DocumentDTO> toDtoList(final List<Document> list) {
		return list.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
