package com.webcbg.eppione.companysettings.rest.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;
import com.webcbg.eppione.companysettings.rest.dto.DocumentDTO;
import com.webcbg.eppione.companysettings.service.DocumentService;

@Component
public class ScheduledTasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	@Autowired
	private DocumentService documentService;

	@Scheduled(fixedRate = 5000)
	public void prepareDocumentsForDelete() {
		List<DocumentDTO> documentList = documentService.getAllByDocumentStatus(DocumentStatus.Draft);
		Date now = new Date();
		for (DocumentDTO doc : documentList) {
			Date updateDate = doc.getUpdateDate();
			long end = now.getTime();
			long start = updateDate.getTime();
			long days = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
			System.out.println("days" + days);

		}
	}

}
