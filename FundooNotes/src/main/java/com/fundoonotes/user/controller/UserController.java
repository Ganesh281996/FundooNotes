package com.fundoonotes.user.controller;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.user.dto.EmailDTO;
import com.fundoonotes.user.dto.LoginDTO;
import com.fundoonotes.user.dto.RegisterUserDTO;
import com.fundoonotes.user.model.Response;
import com.fundoonotes.user.service.UserService;

@RestController()
@RequestMapping(value="/user/")
public class UserController 
{
	@Autowired
	UserService userService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@PostMapping(value="/demo")
	public void demo()
	{
		rabbitTemplate.convertAndSend("mailexchange", "mailroutingkey", "adgfhdg");
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<Response> register(@Valid @RequestBody RegisterUserDTO registerUserDTO)
	{
		userService.register(registerUserDTO);
		return new ResponseEntity<>(new Response("User Registered Successfully"), HttpStatus.OK);
	}
	
	@PutMapping(value="/activateuser/{token}")
	public ResponseEntity<Response> verifyEmail(@PathVariable("token") String token)
	{
		userService.activateUser(token);
		return new ResponseEntity<>(new Response("User Email has been Verified"),HttpStatus.OK);
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginDTO loginDTO)
	{
		userService.login(loginDTO);
		return new ResponseEntity<Response>(new Response("User has successfully Loggedin"), HttpStatus.OK);
	}
	
	@PostMapping(value="/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@Valid @RequestBody EmailDTO emailDTO)
	{
		userService.forgotPassword(emailDTO);
		return new ResponseEntity<Response>(new Response("Link to reset password has been sent to your Email"), HttpStatus.OK);
	}

	@PutMapping(value="/forgotpassword/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@PathVariable String token,String password1,String password2)
	{
		userService.resetPassword(token, password1,password2);
		return new ResponseEntity<Response>(new Response("Password has been changed sucessfully"), HttpStatus.OK);
	}
	
//	
//	@GetMapping(value="/forgotpassword/resetpassword/{token}")
//	public ResponseEntity<Response> verifyToken(@PathVariable String token)
//	{
//		userService.verifyToken(token);
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
}