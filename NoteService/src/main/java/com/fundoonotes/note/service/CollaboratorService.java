package com.fundoonotes.note.service;

import java.util.List;

import com.fundoonotes.note.model.Collaborator;

public interface CollaboratorService 
{
	void addCollaborator(String noteId,String sharedTo,String token);
	
	List<Collaborator> getCollaborators(String noteId,String ownerId);
	
	void removeCollaborator(String noteId,String collaboratorId,String ownerId);
}