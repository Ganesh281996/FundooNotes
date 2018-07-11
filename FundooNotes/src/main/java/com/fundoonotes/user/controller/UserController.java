package com.fundoonotes.user.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.user.dto.EmailDTO;
import com.fundoonotes.user.dto.LoginDTO;
import com.fundoonotes.user.model.User;
import com.fundoonotes.user.service.UserService;
import com.fundoonotes.utility.Response;

@RestController()
@RequestMapping(value="/user/")
public class UserController 
{
	@Autowired
	UserService userService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	Response response;
	
	private static final Logger LOGGER=Logger.getLogger(UserController.class.getName());
	
	@PostMapping(value="/register")
	public ResponseEntity<Response> register(@Valid @RequestBody User user)
	{
		LOGGER.info("Processing POST request of User registration in URL /user/register ");
		LOGGER.info("PARAMETERS : User = "+user.toString());
		
		response = userService.register(user);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@PutMapping(value="/activateuser/{token}")
	public ResponseEntity<Response> verifyEmail(@PathVariable("token") String token)
	{
		LOGGER.info("Processing PUT request of verifying token in URL /user/verify/{token} ");
		LOGGER.info("PARAMETERS : token = "+token);
		
		response=userService.activateUser(token);
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginDTO loginDTO)
	{
		LOGGER.info("Processing POST request for user login in URL /user/login ");
		LOGGER.info("PARAMETERS : UserDTO = "+loginDTO);
		
		response = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@PostMapping(value="/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@Valid @RequestBody EmailDTO emailDTO)
	{
		LOGGER.info("Processing POST request for forgot password in URL /user/forgotpassword ");
		LOGGER.info("PARAMETERS : EmailDTO = "+emailDTO);
		
		response = userService.forgotPassword(emailDTO.getEmail());
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@GetMapping(value="/forgotpassword/resetpassword/{token}")
	public ResponseEntity<Response> verifyToken(@PathVariable String token)
	{
		LOGGER.info("Processing GET request for reset password in URL /user/forgotpassword/resetpassword/{token} ");
		LOGGER.info("PARAMETERS : Token = "+token);
		
		response = userService.verifyToken(token);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@PutMapping(value="/forgotpassword/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@PathVariable String token,String password1,String password2)
	{
		LOGGER.info("Processing PUT request for reset password in URL /user/forgotpassword/resetpassword/{token} ");
		LOGGER.info("PARAMETERS : Password1 = "+password1+" , Password2 = "+password2+" , Token = "+token);
		
		response = userService.resetPassword(token, password1,password2);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
}