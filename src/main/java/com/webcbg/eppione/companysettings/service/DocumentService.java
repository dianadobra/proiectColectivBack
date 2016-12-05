package com.webcbg.eppione.companysettings.service;

import java.io.IOException;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webcbg.eppione.companysettings.convertor.DocumentConverter;
import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.model.Document.ApprovalStatus;
import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;
import com.webcbg.eppione.companysettings.repository.DocumentRepository;
import com.webcbg.eppione.companysettings.rest.dto.DocumentDTO;
import com.webcbg.eppione.companysettings.service.errors.InvalidDataException;

@Service
@Transactional
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentConverter documentConverter;

	@Autowired
	private FileService fileService;

	public DocumentDTO addDocument(DocumentDTO documentDTO, final MultipartFile document) {

		Random random = new Random();

		Document doc = new Document();
		doc = documentConverter.toEntity(documentDTO, doc);
		doc.setApprovalStatus(ApprovalStatus.Unapproved);
		doc.setDocumentState(DocumentStatus.Draft);
		doc.setGuid(random.nextInt(999 - 100) + 100);

		if (document != null) {
			doc.setName(document.getOriginalFilename());
			String path;
			try {
				path = fileService.uploadFile(document,
						fileService.getFilePath(document, "cpds", Long.valueOf(random.nextInt(999) + 1)));
				doc.setDocumentUrl(path);
			} catch (IOException e) {
				throw new InvalidDataException("Error uploading file!", "file.upload.fail");
			}
		}
		doc = documentRepository.save(doc);

		return documentConverter.toDTO(doc);
	}
}
