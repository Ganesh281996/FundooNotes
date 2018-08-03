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

import com.fundoonotes.exception.PasswordMismatchException;
import com.fundoonotes.exception.UserNotFoundException;
import com.fundoonotes.user.dto.EmailDTO;
import com.fundoonotes.user.dto.LoginDTO;
import com.fundoonotes.user.dto.RegisterUserDTO;
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

		String token=jwtTokenService.getJwtToken(user.getUserId());

		emailService.sendVerificationEmail(user.getEmail(), token);
	}

	@Override
	public void activateUser(String token) 
	{
		String userId=jwtTokenService.verifyToken(token);
		
		User user=userDao.findByUserId(userId);
		if(user == null)
		{
			throw new UserNotFoundException(environment.getProperty("UserNotFoundException"));
		}
		user.setVerified(true);
		userDao.save(user);
		LOGGER.info("User Email has been Verified");
	}

	@Override
	public String login(LoginDTO loginDTO) 
	{
		User user = userDao.findByEmail(loginDTO.getEmail());
		if(user == null)
		{
			throw new UserNotFoundException(environment.getProperty("UserNotFoundException"));
		}
		if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
		{
			throw new PasswordMismatchException(environment.getProperty("PasswordMismatchException"));
		}
		return jwtTokenService.getJwtToken(user.getUserId());
	}

	@Override
	public void forgotPassword(EmailDTO emailDTO) 
	{
		User user = userDao.findByEmail(emailDTO.getEmail());
		if(user == null)
		{
			throw new UserNotFoundException(environment.getProperty("UserNotFoundException"));
		}
		String token = jwtTokenService.getJwtToken(user.getUserId());
		emailService.sendResetPasswordEmail(emailDTO.getEmail(), token);
	}

	@Override
	public void resetPassword(String token,String password1,String password2) 
	{
		if(!password1.equals(password2))
		{
			throw new PasswordMismatchException(environment.getProperty("PasswordMismatchException"));
		}
		String userId=jwtTokenService.verifyToken(token);

		User user = userDao.findByUserId(userId);
		if(user == null)
		{
			throw new UserNotFoundException(environment.getProperty("UserNotFoundException"));
		}

		user.setPassword(bCryptPasswordEncoder.encode(password1));
		userDao.save(user);
		LOGGER.info("Password has been changed");
	}

	@Override
	public User getUserByEmail(String email) 
	{
		return userDao.findByEmail(email);
	}

	@Override
	public void verifyToken(String token) 
	{
		jwtTokenService.verifyToken(token);
	}

}