package com.webcbg.eppione.companysettings.rest;

import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webcbg.eppione.companysettings.rest.dto.DocumentDTO;
import com.webcbg.eppione.companysettings.rest.util.ErrorInfoFactory;
import com.webcbg.eppione.companysettings.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentResource {

	@Autowired
	private DocumentService documentService;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestParam(name = "file", required = false) final MultipartFile file,
			@Valid @ModelAttribute DocumentDTO documentDTO, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("Document fields are incorrect", "document", result),
					HttpStatus.BAD_REQUEST);
		}

		DocumentDTO document = documentService.addDocument(documentDTO, file);
		return new ResponseEntity<>(document, HttpStatus.OK);
	}

	@RequestMapping(value = "/{docId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("docId") long docId,
			@RequestParam(name = "file", required = false) final MultipartFile file,
			@Valid @ModelAttribute DocumentDTO documentDTO, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(
					ErrorInfoFactory.buildBadRequestErrorInfo("Document fields are incorrect", "document", result),
					HttpStatus.BAD_REQUEST);
		}

		DocumentDTO document = documentService.updateDocument(docId, documentDTO, file);
		return new ResponseEntity<>(document, HttpStatus.OK);
	}

	@RequestMapping(value = "/{docId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteDocument(@PathVariable("docId") long docId) {
		documentService.deleteDocument(docId);
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = "/{docId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getDocument(@PathVariable("docId") long docId, HttpServletResponse response) {

		final HttpHeaders responseHeaders = new HttpHeaders();
		DocumentDTO doc = documentService.getDocument(docId);
		byte[] document = documentService.downloadDocument(docId);
		try {
			String mimeType = URLConnection.guessContentTypeFromName(doc.getName());
			responseHeaders.setContentType(MediaType.valueOf(mimeType));
			response.setContentType(mimeType);

		} catch (Exception e) {
			responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		}
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", doc.getName()));
		if (document != null) {
			return new ResponseEntity<>(document, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(document, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/template", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadTemplate(HttpServletResponse response) {

		final HttpHeaders responseHeaders = new HttpHeaders();
		byte[] document = documentService.downloadTemplate();
		try {
			String mimeType = URLConnection.guessContentTypeFromName("template.docx");
			responseHeaders.setContentType(MediaType.valueOf(mimeType));
			response.setContentType(mimeType);

		} catch (Exception e) {
			responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		}
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "template.docx"));
		if (document != null) {
			return new ResponseEntity<>(document, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(document, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DocumentDTO>> getAllUserDocuments(@PathVariable final Long userId) {
		final List<DocumentDTO> documents = documentService.getAllDocuments(userId);
		return new ResponseEntity<>(documents, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{docId}/status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeStatus(@PathVariable("docId") long docId,
			@RequestParam(name = "status", required = true) final String status) {
		documentService.changeStatus(status, docId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{docId}/user/{userId}/sign", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("docId") long docId, @PathVariable("userId") long userId) {
		documentService.sign(docId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
