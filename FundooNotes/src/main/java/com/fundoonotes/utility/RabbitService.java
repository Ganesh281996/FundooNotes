package com.fundoonotes.utility;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fundoonotes.user.model.Mail;

@Service
@PropertySource("classpath:rabbit.properties")
public class RabbitService 
{	
	@Autowired
	Environment environment;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
//	@Value("${exchange}")
//	private String exchange;
//	
//	@Value("${routingkey}")
//	private String routingkey;	
	
	public void send(Mail mail) 
	{
		rabbitTemplate.convertAndSend(environment.getProperty("exchange")
				, environment.getProperty("routingkey"), mail);
		System.out.println("Send msg = " + mail);
	}
}