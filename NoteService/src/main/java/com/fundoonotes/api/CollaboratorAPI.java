package com.fundoonotes.api;

public class CollaboratorAPI 
{
	public static final String COLLABORATOR = "/note/collaborator";
	
	public static final String COLLABORATOR_ADD = "/add/{noteId}";
	
	public static final String COLLABORATOR_READ = "/read/{noteId}";

	public static final String COLLABORATOR_REMOVE = "/remove/{noteId}/{collaboratorId}";
}