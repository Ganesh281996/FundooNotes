package com.fundoonotes.note.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonAuthoritiveResourceException extends RuntimeException 
{
	private static final long serialVersionUID = -5822795001601643327L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NonAuthoritiveResourceException.class);

	public NonAuthoritiveResourceException(String message) 
	{
		super(message);
		LOGGER.error(message);
	}
}