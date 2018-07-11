package com.fundoonotes.note.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.utility.JwtTokenService;
import com.fundoonotes.note.utility.Response;

@Transactional
@Service
public class LabelServiceImpl implements LabelService 
{
	private static final Logger LOGGER = Logger.getLogger(LabelServiceImpl.class.getName());

	@Autowired
	LabelDao labelDao;

	@Autowired
	NoteDao noteDao;

	@Autowired
	Userdao userDao;

	@Autowired
	JwtTokenService jwtTokenService;

	@Override
	public Response create(Label label,String token) 
	{
		Response response = new Response();
		
		String userId = jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		label.setUser(userDao.findByUserId(userId));
		label = labelDao.save(label);
		LOGGER.info("Saved Label");
		
		response.setMessage("Saved Label");
		response.setData(label);
		response.setHttpStatus(HttpStatus.CREATED);
		return response;
	}

	@Override
	public Response update(Label label,String token) 
	{
		Response response = new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		label = labelDao.save(label);
		LOGGER.info("Updated Label");
		
		response.setMessage("Updated Label");
		response.setData(label);
		response.setHttpStatus(HttpStatus.CREATED);
		return response;
	}

	@Override
	public Response delete(String labelId,String token) 
	{
		Response response = new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		labelDao.deleteById(labelId);
		LOGGER.info("Deleted Label");
		
		response.setMessage("Deleted Label");
		response.setHttpStatus(HttpStatus.CREATED);
		return response;
	}

	@Override
	public Response read(String token) 
	{
		Response response = new Response();
		
		String userId = jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		response.setMessage("Displaying Labels");
		LOGGER.info("Displaying Labels");
		
		response.setData(labelDao.findByUser_UserId(userId));
		response.setHttpStatus(HttpStatus.FOUND);
		return response;
	}
}