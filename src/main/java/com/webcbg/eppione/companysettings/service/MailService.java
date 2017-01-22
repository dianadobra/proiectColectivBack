package com.webcbg.eppione.companysettings.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

	private final Logger log = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;

	@Async
	public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {

		log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart, isHtml, to, subject, content);

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
			message.setTo(to);
			message.setFrom("projectwave@iteam.com");
			message.setSubject(subject);
			message.setText(content, isHtml);
			javaMailSender.send(mimeMessage);
			log.debug("Sent e-mail to User '{}'", to);
		} catch (Exception e) {
			log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
			throw new MailSendException(e.getMessage());
		}
	}
	public List<String> getEmailsListFromString(String emailsString) {
		List<String> emailsList = new ArrayList<String>();
		List<String> companyCCFromCompanyItems;
		if (emailsString != null) {
			companyCCFromCompanyItems = Arrays.asList(emailsString.split(" "));
			for (String email : companyCCFromCompanyItems) {

				emailsList.add(email.trim());
			}
		}
		return emailsList;
	}
	
	public void sendMail2(String to, String subject, String content){

		// Set up the SMTP server.
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", "smtp.myisp.com");
		Session session = Session.getDefaultInstance(props, null);

		String from = "iTeam@projectwave.com";
		Message msg = new MimeMessage(session);
		try {
		    msg.setFrom(new InternetAddress(from));
		    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    msg.setSubject(subject);
		    msg.setText(content);

		    // Send the message.
		    Transport.send(msg);
		} catch (MessagingException e) {
		    e.printStackTrace();
		}
	}
	

}
