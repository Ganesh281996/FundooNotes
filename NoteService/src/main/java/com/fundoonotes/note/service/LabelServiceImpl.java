package com.fundoonotes.note.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.utility.JwtTokenService;
import com.fundoonotes.note.utility.Response;

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
			label.setUser(userDao.findByUserId(userId));
			label = labelDao.save(label);
			LOGGER.info("Saved Label");
			response.setMessage("Saved Label");
			response.setData(label);
			response.setHttpStatus(HttpStatus.CREATED);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Unable to save Label");
			response.setMessage("Unable to save Label");
			response.setHttpStatus(HttpStatus.CONFLICT);
			return response;
		}
	}

	@Override
	public Response update(Label label,String token) 
	{
		Response response = new Response();
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
			label = labelDao.save(label);
			LOGGER.info("Updated Label");
			response.setMessage("Updated Label");
			response.setData(label);
			response.setHttpStatus(HttpStatus.CREATED);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Unable to Update Label");
			response.setMessage("Unable to Update Label");
			response.setHttpStatus(HttpStatus.CONFLICT);
			return response;
		}
	}

	@Override
	public Response delete(String labelId,String token) 
	{
		Response response = new Response();
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
		try
		{
			labelDao.deleteById(labelId);
			LOGGER.info("Deleted Label");
			response.setMessage("Deleted Label");
			response.setHttpStatus(HttpStatus.CREATED);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Unable to Delete Label");
			response.setMessage("Unable to Delete Label");
			response.setHttpStatus(HttpStatus.CONFLICT);
			return response;
		}
	}

	@Override
	public Response read(String token) 
	{
		Response response = new Response();
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
			response.setMessage("Displaying Labels");
			LOGGER.info("Displaying Labels");
			response.setData(labelDao.findByUser_UserId(userId));
			response.setHttpStatus(HttpStatus.FOUND);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.info("Unable to Display Labels");
			response.setMessage("Unable to Display Labels");
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			return response;
		}
	}
}