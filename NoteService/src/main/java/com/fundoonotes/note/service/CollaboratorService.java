package com.fundoonotes.note.service;

import com.fundoonotes.utility.Response;

public interface CollaboratorService 
{
	Response addCollaborator(String noteId,String sharedBy,String sharedTo);
}