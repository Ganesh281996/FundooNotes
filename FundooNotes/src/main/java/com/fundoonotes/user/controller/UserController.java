package com.fundoonotes.user.controller;

import java.util.logging.Logger;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.user.model.User;
import com.fundoonotes.user.model.UserDTO;
import com.fundoonotes.user.service.UserService;
import com.fundoonotes.utility.Response;

@RestController()
@RequestMapping(value="/user")
public class UserController 
{
	@Autowired
	UserService userService;
	
//	@Autowired
//	RedisTemplate<String, User> redisTemplate;
	
	Response response;
	
	private static final Logger LOGGER=Logger.getLogger(UserController.class.getName());
	
	@PostMapping(value="/register")
//	@Cacheable(value="users",key="#user")
	public ResponseEntity<Response> register(@Valid @RequestBody User user)
	{
		LOGGER.info("Processing post request of User registration in URL /user ");
		LOGGER.info("PARAMETERS : User = "+user.toString());
		
		response = userService.register(user);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}
	
	@PutMapping(value="/verify/{token}")
	public ResponseEntity<Response> verifyEmail(@PathVariable("token") String token)
	{
		LOGGER.info("Processing Put request of verifying token in URL /user/verify/{token} ");
		LOGGER.info("PARAMETERS : token = "+token);
		
		response=userService.verifyToken(token);
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<Response> login(@Valid @RequestBody UserDTO userDTO)
	{
		LOGGER.info("Processing Post request for user login in URL /user/verify/{token} ");
		LOGGER.info("PARAMETERS : UserDTO = "+userDTO);
		
		response = userService.login(userDTO.getEmail(), userDTO.getPassword());
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
}