package com.fundoonotes.note.exception;

public class NoteNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = -778395589303875591L;
	
	public NoteNotFoundException(String message) 
	{
		super(message);
	}
}