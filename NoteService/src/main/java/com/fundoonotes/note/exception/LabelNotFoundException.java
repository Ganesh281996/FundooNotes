package com.fundoonotes.note.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = -1604502402765379093L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LabelNotFoundException.class);

	public LabelNotFoundException(String message) 
	{
		super(message);
		LOGGER.error(message);
	}
}