package com.fundoonotes.utility;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.exception.NonAuthoritiveResourceException;

@Service
@PropertySource(value = "classpath:exception.properties")
public class AuthorizeService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeService.class);
	
	@Autowired
	Environment environment;
	
	@Autowired
	NoteDao noteDao;
	
	@Autowired
	LabelDao labelDao;
	
	public void authorizeUserWithNote(String ownerId,String noteId)
	{
		if(noteDao.findByNoteId(noteId).getOwnerId().equals(ownerId))
		{
			LOGGER.info("User has been authorized to access Note");
			return;
		}
		throw new NonAuthoritiveResourceException(environment.getProperty("NonAuthoritiveResourceException"));
	}
	
	public void authorizeUserWithLabel(String ownerId,String labelId)
	{
		if(labelDao.findByLabelId(labelId).getUserId().equals(ownerId))
		{
			LOGGER.info("User has been authorized to access Label");
			return;
		}
		throw new NonAuthoritiveResourceException(environment.getProperty("NonAuthoritiveResourceException"));
	}
	
//	@RabbitListener(queues = "mailqueue")
//	public void sendEmail(Object object) 
//	{
//		System.out.println(object);
//		System.out.println("doneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//	}
}