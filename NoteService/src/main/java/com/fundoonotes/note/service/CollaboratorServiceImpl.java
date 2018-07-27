package com.fundoonotes.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.CollaboratorDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.exception.NoteNotFoundException;
import com.fundoonotes.note.model.Collaborator;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.utility.AuthorizeService;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@Service
@PropertySource(value = "classpath:exception.properties")
public class CollaboratorServiceImpl implements CollaboratorService 
{
	private static final Logger LOGGER = Logger.getLogger(CollaboratorServiceImpl.class.getName());
	
	@Autowired
	Environment environment; 
	
	@Autowired
	CollaboratorDao collaboratorDao;
	
	@Autowired
	AuthorizeService authorizeService; 
	
	@Autowired
	NoteDao noteDao;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Override
	public void addCollaborator(String noteId, String sharedTo,String ownerId) 
	{	
		authorizeService.authorizeUserWithNote(ownerId, noteId);
		Note note = noteDao.findByNoteId(noteId);
		List<String> collaborators = note.getCollaborators();
		
		if(collaborators == null)
		{
			collaborators = new ArrayList<>();
		}
		
		Collaborator collaborator = new Collaborator();
		collaborator.setSharedBy(ownerId);
		collaborator.setSharedTo(sharedTo);
		collaborator.setNoteId(noteId);
		collaborator = collaboratorDao.save(collaborator);
		
		collaborators.add(collaborator.getSharedTo());
		note.setCollaborators(collaborators);
		noteDao.save(note);
	}

	@Override
	public List<Collaborator> getCollaborators(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);
		
//		Note note = noteDao.findByNoteId(noteId);
//		List<String> collaborators = note.getCollaborators();
		
		return collaboratorDao.findByNoteId(noteId);
	}

	@Override
	public void removeCollaborator(String noteId, String collaboratorId,String ownerId) 
	{
		Note note = noteDao.findByNoteId(noteId);
		List<String> collaborators = note.getCollaborators();
		collaborators.remove(collaboratorId);
		note.setCollaborators(collaborators);
		noteDao.save(note);
		collaboratorDao.deleteByCollaboratorId(collaboratorId);
	}
}