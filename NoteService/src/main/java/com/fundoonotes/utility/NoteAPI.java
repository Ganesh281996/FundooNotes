package com.fundoonotes.utility;

public class NoteAPI 
{
	public static final String NOTE = "/note"; 
	
	public static final String CREATE = "/create";
	public static final String UPDATE = "/update";
	public static final String DELETE = "/delete/{noteId}";
	public static final String READ = "/read";
	
	public static final String PIN = "/pin/{noteId}";
	public static final String ARCHIEVE = "/archieve/{noteId}";
	public static final String TRASH = "/trash/{noteId}";
	
	public static final String LABEL = "/addlabel/{noteId}/{labelId}";

	public static final String ADD_COLLABORATOR = "/collaborator/{noteId}/{email}";
}