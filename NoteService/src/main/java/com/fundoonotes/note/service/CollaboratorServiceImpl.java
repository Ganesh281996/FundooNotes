package com.fundoonotes.note.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.ElasticSearchDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dto.ResponseNoteDTO;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.utility.AuthorizeService;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.NoteOperationsService;

@Service
@PropertySource(value = "classpath:exception.properties")
public class CollaboratorServiceImpl implements CollaboratorService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CollaboratorServiceImpl.class);

	@Autowired
	Environment environment; 

	@Autowired
	AuthorizeService authorizeService; 

	@Autowired
	NoteDao noteDao;

	@Autowired
	JwtTokenService jwtTokenService;
	
	@Autowired
	ElasticSearchDao elasticSearchDao; 
	
	@Autowired
	NoteOperationsService noteOperationsService;
	
	@Autowired
	ModelMapper modelMapper; 

	@Override
	public ResponseNoteDTO addCollaborator(String noteId, String collaboratorEmail, String ownerId) 
	{
		User user = noteOperationsService.collaboratorValidations(noteId, collaboratorEmail);
		Note note = noteDao.findByNoteId(noteId);
		
		note = noteOperationsService.updateCollaborator(note, user);
		note = noteDao.save(note);
		elasticSearchDao.updateNote(note);
		return modelMapper.map(note, ResponseNoteDTO.class);
	}

	@Override
	public ResponseNoteDTO removeCollaborator(String noteId, String collaboratorEmail, String ownerId) 
	{
		noteOperationsService.collaboratorValidations(noteId, collaboratorEmail);
		Note note = noteDao.findByNoteId(noteId);
		
		note = noteOperationsService.removeCollaborator(note, collaboratorEmail);
		note = noteDao.save(note);
		elasticSearchDao.updateNote(note);
		return modelMapper.map(note, ResponseNoteDTO.class);
	}
}