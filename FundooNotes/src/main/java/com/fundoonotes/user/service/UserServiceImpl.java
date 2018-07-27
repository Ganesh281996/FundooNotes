package com.fundoonotes.user.service;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.exception.UserNotFoundException;
import com.fundoonotes.user.dto.RegisterUserDTO;
import com.fundoonotes.user.model.Mail;
import com.fundoonotes.user.model.User;
import com.fundoonotes.user.repository.UserDao;
import com.fundoonotes.utility.EmailService;
import com.fundoonotes.utility.JwtTokenService;

@Service
@Transactional
@PropertySource(value = "classpath:exception.properties")
public class UserServiceImpl implements UserService 
{
	@Autowired
	Environment environment; 
	
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
		
	@Autowired
	ModelMapper modelMapper; 
		
	@Autowired
	AmqpTemplate amqpTemplate;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public void register(RegisterUserDTO registerUserDTO)
	{
		User user = modelMapper.map(registerUserDTO, User.class);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		user=userDao.save(user);
		LOGGER.info("User Registered Successfully . Verification status = false");
		
		String token=jwtTokenService.getJwtToken(user.get_id());
		try
		{
			Mail mail = emailService.createVerificationEmail(user.getEmail(), token);
			amqpTemplate.convertAndSend("mailexchange", "mailkey", mail);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			LOGGER.warn("Unable to send Email");
		}
	}

	@Override
	public void activateUser(String token) 
	{
		String _id=null;
		try
		{
			_id=jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			LOGGER.warn("Invalid Token unable to verify");
		}
		User user=userDao.findBy_id(_id);
		if(user!=null)
		{
			user.setVerified(true);
			userDao.save(user);
			LOGGER.info("User Email has been Verified");
		}
		LOGGER.warn("User with Id present in token was not found");
	}

	@Override
	public String login(String email, String password) 
	{
		User user = userDao.findByEmail(email);
		if(user == null)
		{
			throw new UserNotFoundException("");
		}
		if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
		{
			throw new 
		}
		LOGGER.info("User with given Email and Password was found");
		LOGGER.info("User has logged in successfully!");
		return jwtTokenService.getJwtToken(user.get_id());
		LOGGER.warn("User with given Email and Password was not found");
	}

	@Override
	public void forgotPassword(String email) 
	{
		User user = userDao.findByEmail(email);
		if(user==null)
		{
			LOGGER.warn("User with given Email was not found");
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
		}
		catch(Exception exception)
		{
			LOGGER.warn("Unable to send Email");
		}
	}

	@Override
	public void resetPassword(String token,String password1,String password2) 
	{
		if(!password1.equals(password2))
		{
			LOGGER.warn("Passwords didnt match");
		}
		String _id = null;
		try
		{
			_id=jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			LOGGER.warn("Invalid Token unable to verify");
		}
		User user = userDao.findBy_id(_id);
		if(user!=null)
		{
			user.setPassword(bCryptPasswordEncoder.encode(password1));
			userDao.save(user);
			LOGGER.info("Password has been changed");
		}
		LOGGER.warn("User with Id present in token was not found");
	}

	@Override
	public void verifyToken(String token) 
	{
		try
		{
			jwtTokenService.verifyToken(token);
			LOGGER.info("Token has been verified");
		}
		catch(Exception exception)
		{
			LOGGER.warn("Invalid Token unable to verify");
		}
	}
}