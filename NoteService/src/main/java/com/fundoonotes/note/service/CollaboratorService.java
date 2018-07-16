package com.fundoonotes.note.service;

import java.util.List;

import com.fundoonotes.note.model.Collaborator;
import com.fundoonotes.utility.Response;

public interface CollaboratorService 
{
	void addCollaborator(String noteId,String sharedTo,String token);
	
	List<String> getCollaborators(String noteId,String token);
	
	void removeCollaborator(String noteId,String collaboratorId,String token);
}