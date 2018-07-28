package com.fundoonotes.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fundoonotes.user.model.Response;

import io.jsonwebtoken.JwtException;

@ControllerAdvice
@PropertySource(value = "classpath:exception.properties")
public class GlobalExceptionHandler 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	Environment environment;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> exception(Exception exception)
	{
		LOGGER.error(environment.getProperty("GlobalException"),exception);
		return new ResponseEntity<>(new Response(environment.getProperty("GlobalException")),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Response> jwtException(JwtException exception)
	{
		LOGGER.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AmqpException.class)
	public ResponseEntity<Response> amqpException(AmqpException exception)
	{
		LOGGER.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> userNotFoundException(UserNotFoundException exception)
	{
		LOGGER.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<Response> passwordMismatchException(PasswordMismatchException exception)
	{
		LOGGER.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
}