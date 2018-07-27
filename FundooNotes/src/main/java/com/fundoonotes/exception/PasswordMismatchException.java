package com.fundoonotes.exception;

public class PasswordMismatchException extends RuntimeException
{
	private static final long serialVersionUID = -7031132479093680479L;

	public PasswordMismatchException(String message) 
	{
		super(message);
	}
}