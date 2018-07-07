package com.fundoonotes.note.exception;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fundoonotes.note.utility.Response;
import com.mongodb.MongoException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice()
public class NoteExceptionHandler 
{
	private static final Logger LOGGER = Logger.getLogger(NoteExceptionHandler.class.getName());
	
	@ExceptionHandler(value = {SignatureException.class,UnsupportedJwtException.class, 
						MalformedJwtException.class,ExpiredJwtException.class})
	public ResponseEntity<Response> signatureException(Exception exception)
	{
		Response response = new Response();
		LOGGER.warning(exception.getMessage());
		response.setMessage(exception.getMessage());
		response.setData(exception);
		response.setHttpStatus(HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Response> illegalArgumentException(Exception exception)
	{
		Response response = new Response();
		LOGGER.warning(exception.getMessage());
		response.setMessage(exception.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<Response> nullPointerException(Exception exception)
	{
		Response response = new Response();
		LOGGER.warning(exception.getMessage());
		response.setMessage(exception.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(value = MongoException.class)
	public ResponseEntity<Response> mongoException(Exception exception)
	{
		Response response = new Response();
		LOGGER.warning(exception.getMessage());
		response.setMessage(exception.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Response> exception(Exception exception)
	{
		Response response = new Response();
		LOGGER.warning(exception.getMessage());
		response.setMessage(exception.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
}