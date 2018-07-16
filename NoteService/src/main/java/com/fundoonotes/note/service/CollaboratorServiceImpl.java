package com.fundoonotes.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.CollaboratorDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.model.Collaborator;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@Service
public class CollaboratorServiceImpl implements CollaboratorService 
{
	private static final Logger LOGGER = Logger.getLogger(CollaboratorServiceImpl.class.getName());
	
	@Autowired
	CollaboratorDao collaboratorDao;
	
	@Autowired
	NoteDao noteDao;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Override
	public void addCollaborator(String noteId, String sharedTo,String ownerId) 
	{		
		String sharedBy = jwtTokenService.verifyToken(ownerId);
		Note note = noteDao.findByNoteId(noteId);
		List<String> collaborators = note.getCollaborators();
		
		if(collaborators == null)
		{
			collaborators = new ArrayList<>();
		}
		
		Collaborator collaborator = new Collaborator();
		collaborator.setSharedBy(sharedBy);
		collaborator.setSharedTo(sharedTo);
		collaborator = collaboratorDao.save(collaborator);
		
		collaborators.add(collaborator.getCollaboratorId());
		note.setCollaborators(collaborators);
		noteDao.save(note);
	}

	@Override
	public List<String> getCollaborators(String noteId,String token) 
	{
		Note note = noteDao.findByNoteId(noteId);
		List<String> collaborators = note.getCollaborators();
		
		return collaborators;
	}

	@Override
	public void removeCollaborator(String noteId, String collaboratorId,String token) 
	{
		Note note = noteDao.findByNoteId(noteId);
		List<String> collaborators = note.getCollaborators();
		collaborators.remove(collaboratorId);
		note.setCollaborators(collaborators);
		noteDao.save(note);
		collaboratorDao.deleteByCollaboratorId(collaboratorId);
	}
}