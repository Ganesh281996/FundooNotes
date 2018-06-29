package com.fundoonotes.utility;

import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailService 
{
	@Autowired
	JavaMailSender javaMailSender;
	
	private static final Logger LOGGER=Logger.getLogger(EmailService.class.getName());
	
	public void sendEmail(String url,String email) throws MessagingException
	{
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText(url , true);
		helper.setSubject("Subject");
		javaMailSender.send(message);
		LOGGER.info("Verification Email has been Sent");
	}
}
