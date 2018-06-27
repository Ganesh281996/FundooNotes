package com.fundoonotes.user.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.user.model.User;
import com.fundoonotes.user.repository.UserDao;
import com.fundoonotes.utility.EmailService;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@Service
@Transactional
public class UserServiceImpl implements UserService 
{
	@Autowired
	UserDao userDao;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Autowired
	EmailService emailService;
	
	Response response;
	
	private static final Logger LOGGER=Logger.getLogger(UserServiceImpl.class.getName());
	
	@Override
	public Response register(User user)
	{
		response=new Response();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		try
		{
			user=userDao.save(user);
			LOGGER.info("User Registered Successfully . Verification status = false");
		}
		catch(Exception exception)
		{
			LOGGER.warning("User already exists OR something went wrong");
			response.setMessage("User already exists OR something went wrong");
			response.setHttpStatus(HttpStatus.CONFLICT);
			return response;
		}
		String token=jwtTokenService.getJwtToken(user.get_id());
		try
		{
			emailService.sendEmail(token,user.getEmail());
			response.setMessage("Verification Email has been Sent");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.info("Unable to send Email");
			response.setMessage("Unable to send Email");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@Override
	public Response verifyToken(String token) 
	{
		response=new Response();
		String _id=null;
		try
		{
			_id=jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			LOGGER.info("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		User user=userDao.findBy_id(_id);
		if(user!=null)
		{
			user.setVerified(true);
			userDao.save(user);
			LOGGER.info("User Email has been Verified");
			response.setMessage("User Email has been verified");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		LOGGER.warning("User with Id present in token was not found");
		response.setMessage("User with Id present in token was not found");
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		return response;
	}

	@Override
	public Response login(String email, String password) 
	{
		response=new Response();
		User user = userDao.findByEmail(email);
		if(user!=null && bCryptPasswordEncoder.matches(password, user.getPassword()))
		{
			LOGGER.info("User with given Email and Password was found");			
			String token = jwtTokenService.getJwtToken(user.get_id());
			LOGGER.info("User has logged in successfully!");
			response.setData(token);
			response.setMessage("User has logged in successfully!");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		LOGGER.warning("User with given Email and Password was not found");
		response.setMessage("User with given Email and Password was not found");
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		return response;
	}
}