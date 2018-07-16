package com.fundoonotes.note.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class GlobalExceptionHandler 
{
	private static final String MESSAGE = "Something went wrong";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception exception)
	{
		LOGGER.error(MESSAGE, exception);
		return new ResponseEntity<>(MESSAGE,HttpStatus.BAD_REQUEST);
	}
}