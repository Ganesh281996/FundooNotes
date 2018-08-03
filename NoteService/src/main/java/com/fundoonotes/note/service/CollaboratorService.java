package com.fundoonotes.note.service;

import com.fundoonotes.note.dto.ResponseNoteDTO;

public interface CollaboratorService 
{
	ResponseNoteDTO addCollaborator(String noteId,String collaboratorEmail,String ownerId);

	ResponseNoteDTO removeCollaborator(String noteId,String collaboratorEmail,String ownerId);
}