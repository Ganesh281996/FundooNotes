package com.fundoonotes.note.service;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@Transactional
@Service
public class NoteServiceImpl implements NoteService 
{
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImpl.class.getName());
	
	@Autowired
	NoteDao noteDao;
	
	@Autowired
	Userdao userDao;
	
	Response response;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Override
	public Response createDummyUser(User user) 
	{
		userDao.save(user);
		return null;
	}
	
	@Override
	public Response createNote(Note note, String token) 
	{
		response=new Response();
		String userId = null;
		try
		{
			userId = jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		try
		{
			note.setUser(userDao.findBy_id(userId));
			note = noteDao.save(note);
			LOGGER.info("Note has been saved");
			response.setMessage("Note has been saved");
			response.setHttpStatus(HttpStatus.OK);
			response.setData(note);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Unable to save Note");
			response.setMessage("Unable to save Note");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@Override
	public Response updateNote(Note note, String token) 
	{
		response = new Response();
		try
		{
			jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		try
		{
			noteDao.save(note);
			LOGGER.info("Note has been update");
			response.setMessage("Note has been updated");
			response.setHttpStatus(HttpStatus.OK);
			response.setData(note);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Unable to update Note");
			response.setMessage("Unable to update Note");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@Override
	public Response deleteNote(String noteId, String token) 
	{
		response=new Response();
		try
		{
			jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		
		int result = noteDao.deleteBy_id(noteId);
		if(result>0)
		{
			LOGGER.info("Note has been deleted");
			response.setMessage("Note has been deleted");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		else
		{
			LOGGER.warning("Unable to delete Note");
			response.setMessage("Unable to delete Note");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@Override
	public Response displayNotes(String token) 
	{
		response=new Response();
		String userId=null;
		try
		{
			userId = jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		LOGGER.info("Displaying Notes");
		response.setData(noteDao.findByUser(userId));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
}