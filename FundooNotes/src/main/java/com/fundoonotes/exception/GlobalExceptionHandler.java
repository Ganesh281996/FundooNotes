package com.fundoonotes.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fundoonotes.user.model.Response;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> exception(Exception exception)
	{
		LOGGER.error("Something went wrong",exception);
		return new ResponseEntity<>(new Response("Something went wrong"),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> userNotFoundException(UserNotFoundException exception)
	{
		LOGGER.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.NOT_FOUND);
	}
}