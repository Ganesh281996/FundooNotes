package com.fundoonotes.note.exception;

public class UserNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 2254889152149646264L;

	public UserNotFoundException(String message)
	{
		super(message);
	}
}