package com.fundoonotes.note.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = -778395589303875591L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteNotFoundException.class);
	
	public NoteNotFoundException(String message) 
	{
		super(message);
		LOGGER.error(message);
	}
}