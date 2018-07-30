package com.fundoonotes.note.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fundoonotes.utility.Response;

@ControllerAdvice()
@PropertySource(value = "classpath:exception.properties")
public class GlobalExceptionHandler 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	Environment environment; 
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Response> exception(Exception exception)
	{
		LOGGER.error(environment.getProperty("GlobalException"), exception);
		return new ResponseEntity<>(new Response(environment.getProperty("GlobalException")),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NoteNotFoundException.class)
	public ResponseEntity<Response> noteNotFoundException(NoteNotFoundException exception)
	{
		LOGGER.error(exception.getMessage(), exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = LabelNotFoundException.class)
	public ResponseEntity<Response> labelNotFoundException(LabelNotFoundException exception)
	{
		LOGGER.error(exception.getMessage(), exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NonAuthoritiveResourceException.class)
	public ResponseEntity<Response> nonAuthoritiveResourceException(NonAuthoritiveResourceException exception)
	{
		LOGGER.error(exception.getMessage(), exception);
		return new ResponseEntity<>(new Response(exception.getMessage()),HttpStatus.UNAUTHORIZED);
	}
}