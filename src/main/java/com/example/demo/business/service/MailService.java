package com.example.demo.business.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.SpringRedditException;
import com.example.demo.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class MailService {

	private final JavaMailSender mailSender;
	
	
	public void sendMail(NotificationEmail notificationEmail) {
		
		MimeMessagePreparator messagePreparator = mimeMessage->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("hikmetnadirler@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(notificationEmail.getBody());
			
		};
		
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent!");
			
		}catch(MailException e) {
			log.error("Exceptiom occured when sending mail", e);
			throw new SpringRedditException("Exception occured when sending mail to"+ notificationEmail.getRecipient(),e);
		}
	};

}
