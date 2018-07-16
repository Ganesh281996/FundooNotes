package com.fundoonotes.utility;

public class CollaboratorAPI 
{
	public static final String COLLABORATOR = "/collaborator";
	
	public static final String COLLABORATOR_ADD = "/add/{noteId}/{ownerId}";
	
	public static final String COLLABORATOR_READ = "/read/{noteId}";

	public static final String COLLABORATOR_REMOVE = "/remove/{noteId}/{collaboratorId}";
}