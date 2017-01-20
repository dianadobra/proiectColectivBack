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
import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;
import com.webcbg.eppione.companysettings.model.Flow;
import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;
import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.DocumentRepository;
import com.webcbg.eppione.companysettings.repository.FlowRepository;
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

	@Autowired
	private LogService logService;
	
	@Autowired
	private FlowRepository flowRepository;

	public DocumentDTO addDocument(DocumentDTO documentDTO, final MultipartFile document) {

		Random random = new Random();

		Document doc = new Document();
		doc = documentConverter.toEntity(documentDTO, doc);
		doc.setDocumentState(DocumentStatus.Draft);
		doc.setAuthor(userService.getUser(documentDTO.getAuthorId()));
		doc.setCreationDate(new Date());
		doc.setUpdateDate(new Date());
		if (documentDTO.getSigned() != null) {
			doc.setSignedBy(userService.getUser(documentDTO.getAuthorId()));
		}
		if (document != null) {
			List<Document> previousDocuments = documentRepository.findAllByName(document.getOriginalFilename());

			doc.setName(document.getOriginalFilename());
			if (previousDocuments.size() > 0) {
				doc.setVersion(previousDocuments.get(previousDocuments.size() - 1).getVersion() + 0.1f);
				doc.setGuid(previousDocuments.get(0).getGuid());
			} else {
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
		} else {
			doc.setVersion(0.1f);
			doc.setGuid(random.nextInt(999 - 100) + 100);
		}
		doc = documentRepository.save(doc);

		// create log for creating new document
		Log log = new Log();
		log.setAction(LogAction.Create);
		Date dateObj = new Date();
		log.setDate(dateObj);
		log.setDescription("Document " + doc.getName() + " created. ");
		log.setEntityId(doc.getId());
		log.setEntityType(LogEntity.Document);
		log.setUser(doc.getAuthor());

		logService.createLog(log);

		return documentConverter.toDTO(doc);
	}

	public DocumentDTO getDocument(Long docId) {
		return documentConverter.toDTO(documentRepository.findOne(docId));
	}

	public List<DocumentDTO> getAllDocuments(Long userId) {
		return documentConverter.toDtoList(documentRepository.findAllByAuthorIdOrderByGuidAsc(userId));
	}

	public List<DocumentDTO> getAllByDocumentStatus(DocumentStatus docState) {
		return documentConverter.toDtoList(documentRepository.findAllByDocumentState(docState));
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
		newDoc.setAbstractInput(
				documentDTO.getAbstractInput() == null ? doc.getAbstractInput() : documentDTO.getAbstractInput());
		newDoc.setKeywords(documentDTO.getKeywords() == null ? doc.getKeywords() : documentDTO.getKeywords());
		newDoc.setGuid(doc.getGuid());
		newDoc.setAuthor(userService.getUser(documentDTO.getAuthorId()));
		if (newDoc.getAuthor().getId() != doc.getAuthor().getId()) {
			newDoc.setDocumentState(DocumentStatus.FinalUpdated);
			newDoc.setVersion(doc.getVersion() + 0.1f);
		} else {
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

		// create log for update document
		Log log = new Log();
		log.setAction(LogAction.Update);
		Date dateObj = new Date();
		log.setDate(dateObj);
		log.setDescription("Document " + doc.getName() + " was updated. ");
		log.setEntityId(doc.getId());
		log.setEntityType(LogEntity.Document);
		log.setUser(doc.getAuthor());

		logService.createLog(log);

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
		
		//remove foreign key from flow_document
		List<Flow> flows = this.flowRepository.findAll();
		for (Flow flow : flows) {
			if(flow.getDocuments().contains(doc)){
				flow.getDocuments().remove(doc);
				flowRepository.save(flow);
			}
		}
		
		// create log for deleting document
		Log log = new Log();
		log.setAction(LogAction.Delete);
		Date dateObj = new Date();
		log.setDate(dateObj);
		log.setDescription("Document " + doc.getName() + " was deleted. ");
		log.setEntityId(doc.getId());
		log.setEntityType(LogEntity.Document);
		log.setUser(doc.getAuthor());

		logService.createLog(log);

		documentRepository.delete(doc);
	}

	public void changeStatus(String status, Long docId) {
		Document doc = documentRepository.findOne(docId);
		if (doc == null) {
			throw new ResourceNotFoundException("Document not found!");
		}
		doc.setDocumentState(DocumentStatus.valueOf(status));
		if (DocumentStatus.valueOf(status).equals(DocumentStatus.Draft)) {
			doc.setVersion(doc.getVersion() / 10);
		} else if (DocumentStatus.valueOf(status).equals(DocumentStatus.Final)) {
			doc.setVersion(doc.getVersion() * 10);
		}

		// create log for changed status
		Log log = new Log();
		log.setAction(LogAction.ChangeStatus);
		Date dateObj = new Date();
		log.setDate(dateObj);
		log.setDescription("Status for document " + doc.getName() + " was changed to: " + status + ".");
		log.setEntityId(doc.getId());
		log.setEntityType(LogEntity.Document);
		log.setUser(doc.getAuthor());

		logService.createLog(log);

		documentRepository.save(doc);
	}

	public void sign(Long docId, Long userId) {
		Document doc = documentRepository.findOne(docId);
		if (doc == null) {
			throw new ResourceNotFoundException("Document not found!");
		}
		User user = userService.getUser(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User not found!");
		}
		doc.setSigned(true);
		doc.setSignedBy(user);
		documentRepository.save(doc);
	}
}
