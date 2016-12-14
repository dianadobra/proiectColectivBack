package com.webcbg.eppione.companysettings.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import com.webcbg.eppione.companysettings.service.errors.ResourceNotFoundException;

@Service
@Transactional
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentConverter documentConverter;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	public DocumentDTO addDocument(DocumentDTO documentDTO, final MultipartFile document) {

		Random random = new Random();

		Document doc = new Document();
		doc = documentConverter.toEntity(documentDTO, doc);
		doc.setApprovalStatus(ApprovalStatus.Unapproved);
		doc.setDocumentState(DocumentStatus.Draft);
		doc.setGuid(random.nextInt(999 - 100) + 100);
		doc.setAuthor(userService.getUser(documentDTO.getAuthorId()));
		doc.setCreationDate(new Date());
		doc.setUpdateDate(new Date());
		doc.setVersion(0.1f);
		if (documentDTO.isSigned()) {
			doc.setSignedBy(userService.getUser(documentDTO.getAuthorId()));
		}

		if (document != null) {
			doc.setName(document.getOriginalFilename());
			String path;
			try {
				path = fileService.uploadFile(document,
						fileService.getFilePath(document, "documents", Long.valueOf(random.nextInt(999) + 1)));
				doc.setDocumentUrl(path);
			} catch (IOException e) {
				throw new InvalidDataException("Error uploading file!", "file.upload.fail");
			}
		}
		doc = documentRepository.save(doc);

		return documentConverter.toDTO(doc);
	}

	public DocumentDTO getDocument(Long docId) {
		return documentConverter.toDTO(documentRepository.findOne(docId));
	}

	public List<DocumentDTO> getAllDocuments(Long userId) {
		return documentConverter.toDtoList(documentRepository.findAllByAuthorId(userId));
	}

	public byte[] downloadDocument(final long docId) {

		final Document doc = documentRepository.findOne(docId);
		if (doc == null) {
			throw new ResourceNotFoundException("Document not found");
		}
		final String path = doc.getDocumentUrl();

		if (path != null) {
			try {
				return fileService.getDocument(path);
			} catch (IOException e) {
				throw new ResourceNotFoundException("Document not found", "document.not.found");
			}
		} else {
			return null;
		}
	}

	public DocumentDTO updateDocument(Long docId, DocumentDTO documentDTO, final MultipartFile document) {

		Random random = new Random();

		Document doc = documentRepository.findOne(docId);
		Document newDoc = documentConverter.toEntity(documentDTO, doc);
		newDoc.setGuid(doc.getGuid());
		newDoc.setApprovalStatus(ApprovalStatus.Unapproved);
		newDoc.setDocumentState(DocumentStatus.Draft);
		newDoc.setAuthor(userService.getUser(documentDTO.getAuthorId()));
		newDoc.setUpdateDate(new Date());
		if (newDoc.getVersion() < 1) {
			newDoc.setVersion(doc.getVersion() + 0.1f);
		} else {
			newDoc.setVersion(doc.getVersion() + 1f);
		}

		if (documentDTO.isSigned()) {
			newDoc.setSignedBy(userService.getUser(documentDTO.getAuthorId()));
		}

		if (document != null) {
			newDoc.setName(document.getOriginalFilename());
			String path;
			try {
				path = fileService.uploadFile(document,
						fileService.getFilePath(document, "documents", Long.valueOf(random.nextInt(999) + 1)));
				newDoc.setDocumentUrl(path);
			} catch (IOException e) {
				throw new InvalidDataException("Error uploading file!", "file.upload.fail");
			}
		}
		newDoc = documentRepository.save(newDoc);

		return documentConverter.toDTO(newDoc);
	}
}
