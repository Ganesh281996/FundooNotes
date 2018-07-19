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

@ControllerAdvice()
@PropertySource(value = "classpath:exception.properties")
public class GlobalExceptionHandler 
{
	private static final String MESSAGE = "Something went wrong";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	Environment environment; 
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception exception)
	{
		LOGGER.error(environment.getProperty("GlobalException"), exception);
		return new ResponseEntity<>(MESSAGE,HttpStatus.BAD_REQUEST);
	}
}