package com.fundoonotes.utility;

import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.fundoonotes.user.model.Mail;

public class EmailService 
{
	@Autowired
	JavaMailSender javaMailSender;
	
	private static final Logger LOGGER=Logger.getLogger(EmailService.class.getName());
	
	public Mail createVerificationEmail(String to,String token)
	{
		Mail mail = new Mail();
		mail.setTo(to);
		mail.setSubject("Verification Email");
		mail.setText("<a href ='http://localhost:8080/user/activateuser/"+token+"'>Verify Email</a>");
		return mail;
	}
	
	@RabbitListener(queues = "mailqueue")
	public void sendEmail(Mail mail) throws MessagingException
	{
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		System.out.println(mail);
		helper.setTo(mail.getTo());
		helper.setText(mail.getText() , true);
		helper.setSubject(mail.getSubject());
		javaMailSender.send(message);
		LOGGER.info("Verification Email has been Sent");
	}
}
