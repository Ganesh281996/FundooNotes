package com.fundoonotes.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fundoonotes.user.model.Mail;

@Service
@PropertySource(value = "classpath:rabbitmq.properties")
public class EmailService 
{
	@Autowired
	Environment environment;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	AmqpTemplate amqpTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	public void sendVerificationEmail(String to,String token)
	{
		Mail mail = new Mail();
		mail.setTo(to);
		mail.setSubject("Verification Email");
		mail.setText("<a href ='http://localhost:8080/user/activateuser/"+token+"'>Verify Email</a>");
		
		amqpTemplate.convertAndSend(environment.getProperty("mail.exchange"), environment.getProperty("mail.routingkey"), mail);
		
	}
	
	public void sendResetPasswordEmail(String to,String token)
	{
		Mail mail = new Mail();
		mail.setTo(to);
		mail.setSubject("Reset Password");
		mail.setText("<a href ='http://localhost:8080/user/forgotpassword/resetpassword/"+token+"'>Reset Password</a>");
		
		amqpTemplate.convertAndSend(environment.getProperty("mail.exchange"), environment.getProperty("mail.routingkey"), mail);
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
