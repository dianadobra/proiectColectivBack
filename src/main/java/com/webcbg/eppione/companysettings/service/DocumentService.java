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
import com.webcbg.eppione.companysettings.model.User;
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
		doc.setAuthor(userService.getUser(documentDTO.getAuthorId()));
		doc.setCreationDate(new Date());
		doc.setUpdateDate(new Date());
		if (documentDTO.getSigned()) {
			doc.setSignedBy(userService.getUser(documentDTO.getAuthorId()));
		}
		if (document != null) {
			List<Document> previousDocuments = this.documentRepository.findAllByName(document.getOriginalFilename());
			
			doc.setName(document.getOriginalFilename());
			if (previousDocuments.size()>0){
				doc.setVersion(previousDocuments.get(previousDocuments.size()-1).getVersion()+0.1f);
				doc.setGuid(previousDocuments.get(0).getGuid());
			}else{
				doc.setVersion(0.1f);
				doc.setGuid(random.nextInt(999 - 100) + 100);
			}

			String path;
			try {
				path = fileService.uploadFile(document,
						fileService.getFilePath(document, "documents", Long.valueOf(random.nextInt(999) + 1)));
				doc.setDocumentUrl(path);
			} catch (IOException e) {
				throw new InvalidDataException("Error uploading file!", "file.upload.fail");
			}
		}else{
			doc.setVersion(0.1f);
			doc.setGuid(random.nextInt(999 - 100) + 100);
		}
		doc = documentRepository.save(doc);

		return documentConverter.toDTO(doc);
	}

	public DocumentDTO getDocument(Long docId) {
		return documentConverter.toDTO(documentRepository.findOne(docId));
	}

	public List<DocumentDTO> getAllDocuments(Long userId) {
		return documentConverter.toDtoList(documentRepository.findAllByAuthorIdOrderByGuidAsc(userId));
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
		Document newDoc = new Document();
		newDoc.setAbstractInput(documentDTO.getAbstractInput()==null ? doc.getAbstractInput() : documentDTO.getAbstractInput());
		newDoc.setKeywords(documentDTO.getKeywords() == null ? doc.getKeywords() : documentDTO.getKeywords());
		newDoc.setGuid(doc.getGuid());
		newDoc.setApprovalStatus(ApprovalStatus.Unapproved);
		newDoc.setAuthor(userService.getUser(documentDTO.getAuthorId()));
		if (newDoc.getAuthor().getId()!=doc.getAuthor().getId()){
			newDoc.setDocumentState(DocumentStatus.FinalUpdated);
			newDoc.setVersion(doc.getVersion() + 0.1f);
		}else{
			newDoc.setDocumentState(DocumentStatus.Draft);
			if (newDoc.getVersion() < 1) {
				newDoc.setVersion(doc.getVersion() + 0.1f);
			} else {
				newDoc.setVersion(doc.getVersion() + 1f);
			}
		}
		newDoc.setUpdateDate(new Date());
		newDoc.setSigned(false);


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
		doc.setDocumentState(DocumentStatus.Draft);
		documentRepository.save(doc);
		newDoc = documentRepository.save(newDoc);

		return documentConverter.toDTO(newDoc);
	}

	public byte[] downloadTemplate() {

		final String path = "C:/Users/Public/iTeam/template.docx";

		try {
			return fileService.getDocument(path);
		} catch (IOException e) {
			throw new ResourceNotFoundException("Document not found", "document.not.found");
		}
	}

	public void deleteDocument(Long docId) {
		Document doc = documentRepository.findOne(docId);
		if (doc == null) {
			throw new ResourceNotFoundException("Document not found!");
		}
		documentRepository.delete(doc);
	}
	
	public void changeStatus(String status, Long docId){
		Document doc = documentRepository.findOne(docId);
		if (doc == null) {
			throw new ResourceNotFoundException("Document not found!");
		}
		doc.setDocumentState(DocumentStatus.valueOf(status));
		if (DocumentStatus.valueOf(status).equals(DocumentStatus.Draft)){
			doc.setVersion(doc.getVersion()/10);
		}else if (DocumentStatus.valueOf(status).equals(DocumentStatus.Final)){
			doc.setVersion(doc.getVersion()*10);
		}
		
		this.documentRepository.save(doc);
	}
	
	public void sign(Long docId, Long userId){
		Document doc = documentRepository.findOne(docId);
		if (doc == null) {
			throw new ResourceNotFoundException("Document not found!");
		}
		User user = this.userService.getUser(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User not found!");
		}
		doc.setSigned(true);
		doc.setSignedBy(user);
		this.documentRepository.save(doc);
	}
}
