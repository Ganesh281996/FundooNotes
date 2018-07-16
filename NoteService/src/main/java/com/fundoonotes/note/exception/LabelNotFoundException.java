package com.fundoonotes.note.exception;

public class LabelNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = -1604502402765379093L;

	public LabelNotFoundException(String message) 
	{
		super(message);
	}
}