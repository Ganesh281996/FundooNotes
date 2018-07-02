package com.fundoonotes.note.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@Service
public class NoteServiceImpl implements NoteService 
{
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImpl.class);
	
	@Autowired
	NoteDao noteDao;
	
	Response response;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Override
	public Response createNote(Note note, String token) 
	{
		response=new Response();
		String id = jwtTokenService.verifyToken(token);
		
		try
		{
			note = noteDao.save(note);
			LOGGER.info("Note has been saved");
			response.setMessage("Note has been saved");
			response.setHttpStatus(HttpStatus.OK);
			response.setData(note);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.error("Unable to save Note");
			response.setMessage("Unable to save Note");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}
}