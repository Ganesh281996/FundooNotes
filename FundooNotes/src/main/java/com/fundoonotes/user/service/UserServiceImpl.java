package com.fundoonotes.user.service;

import java.util.logging.Logger;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.user.model.Mail;
import com.fundoonotes.user.model.User;
import com.fundoonotes.user.repository.RedisRepository;
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
	
//	@Autowired
//	RedisRepository redisRepository;
	
	@Autowired
	AmqpTemplate amqpTemplate;
	
	private static final Logger LOGGER=Logger.getLogger(UserServiceImpl.class.getName());
	
	@Override
	public Response register(User user)
	{
		response=new Response();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		try
		{
			user=userDao.save(user);
//			redisRepository.save(user);
//			System.out.println(redisRepository.getAllUsers());
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
			Mail mail = new Mail();
			mail.setTo(user.getEmail());
			mail.setSubject("Verification Email");
			mail.setText("<a href ='http://localhost:8080/user/activateuser/"+token+"'>Verify Email</a>");
			amqpTemplate.convertAndSend("mailexchange", "mailkey", mail);
//			emailService.sendEmail("<a href ='http://localhost:8080/user/activateuser/"+token+"'>Verify Email</a>",user.getEmail());
			response.setMessage("Verification Email has been Sent");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			LOGGER.warning("Unable to send Email");
			response.setMessage("Unable to send Email");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@Override
	public Response activateUser(String token) 
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
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		User user=userDao.findBy_id(_id);
		if(user!=null)
		{
			user.setVerified(true);
			userDao.save(user);
//			redisRepository.save(user);
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

	@Override
	public Response forgotPassword(String email) 
	{
		response=new Response();
		User user = userDao.findByEmail(email);
		if(user==null)
		{
			LOGGER.warning("User with given Email was not found");
			response.setMessage("User with given Email was not found");
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			return response;
		}
		
		String token = jwtTokenService.getJwtToken(user.get_id());
		try
		{
			Mail mail = new Mail();
			mail.setTo(email);
			mail.setSubject("Verification Email");
			mail.setText("<a href ='http://localhost:8080/user/forgotpassword/resetpassword/"+token+"'>Reset Password</a>");
			amqpTemplate.convertAndSend("MailExchange", "MailKey", mail);
//			emailService.sendEmail("<a href ='http://localhost:8080/user/forgotpassword/resetpassword/"+token+"'>Reset Password</a>", email);
			response.setMessage("Email has been Sent");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Unable to send Email");
			response.setMessage("Unable to send Email");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
	}

	@Override
	public Response resetPassword(String token,String password1,String password2) 
	{
		response=new Response();
		if(!password1.equals(password2))
		{
			LOGGER.warning("Passwords didnt match");
			response.setMessage("Passwords didnt match");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		String _id = null;
		try
		{
			_id=jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
		User user = userDao.findBy_id(_id);
		if(user!=null)
		{
			user.setPassword(bCryptPasswordEncoder.encode(password1));
			userDao.save(user);
//			redisRepository.save(user);
			LOGGER.info("Password has been changed");
			response.setMessage("Password has been changed");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		LOGGER.warning("User with Id present in token was not found");
		response.setMessage("User with Id present in token was not found");
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		return response;
	}

	@Override
	public Response verifyToken(String token) 
	{
		try
		{
			jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
			response.setMessage("Token has been verified");
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		catch(Exception exception)
		{
			LOGGER.warning("Invalid Token unable to verify");
			response.setMessage("Invalid Token unable to verify");
			response.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			return response;
		}
	}
}