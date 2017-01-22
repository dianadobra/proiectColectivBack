package com.webcbg.eppione.companysettings.rest.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webcbg.eppione.companysettings.model.Document;
import com.webcbg.eppione.companysettings.model.Document.DocumentStatus;
import com.webcbg.eppione.companysettings.model.Log;
import com.webcbg.eppione.companysettings.model.Log.LogAction;
import com.webcbg.eppione.companysettings.model.Log.LogEntity;
import com.webcbg.eppione.companysettings.repository.DocumentRepository;
import com.webcbg.eppione.companysettings.service.LogService;

@Component
public class ScheduledTasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private LogService logService;

	@Scheduled(fixedRate = 5000)
	public void prepareDocumentsForDelete() {
		List<Document> documentList = documentRepository.findAllByDocumentState(DocumentStatus.Draft);
		Date now = new Date();
		for (Document doc : documentList) {
			Date updateDate = doc.getUpdateDate();
			long end = now.getTime();
			long start = updateDate.getTime();
			long days = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));

			System.out.println("days " + days);
			if (days >= 30) {
				// send mail
				if (!doc.isPreparedForDelete()){
					doc.setPreparedForDelete(true);
					doc.setUpdateDate(new Date());
					documentRepository.save(doc);

					Log log = new Log();
					log.setAction(LogAction.PrepoareForDelete);
					Date dateObj = new Date();
					log.setDate(dateObj);
					log.setDescription("Document " + doc.getName() + " prepared for delete. ");
					log.setEntityId(doc.getId());
					log.setEntityType(LogEntity.Document);
					log.setUser(doc.getAuthor());

					logService.createLog(log);
				}
				
			}
		}
	}

	@Scheduled(fixedRate = 5000)
	public void deleteDocuments() {
		List<Document> documentList = documentRepository
				.findAllByDocumentStateAndPreparedForDelete(DocumentStatus.Draft, true);
		Date now = new Date();
		for (Document doc : documentList) {
			Date updateDate = doc.getUpdateDate();
			long end = now.getTime();
			long start = updateDate.getTime();
			long days = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
			System.out.println("days " + days);
			if (days >= 30) {
				// send mail

				documentRepository.delete(doc);

				Log log = new Log();
				log.setAction(LogAction.Delete);
				Date dateObj = new Date();
				log.setDate(dateObj);
				log.setDescription("Document " + doc.getName() + " deleted. ");
				log.setEntityId(doc.getId());
				log.setEntityType(LogEntity.Document);
				log.setUser(doc.getAuthor());

				logService.createLog(log);
			}
		}
	}

}
