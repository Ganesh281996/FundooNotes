package com.fundoonotes.exception;

public class UserNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = -5025260293143758065L;

	public UserNotFoundException(String message) 
	{
		super(message);
	}
}