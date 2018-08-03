package com.fundoonotes.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fundoonotes.feignclient.UserServiceFeignClient;
import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.exception.NoteNotFoundException;
import com.fundoonotes.note.exception.UserNotFoundException;
import com.fundoonotes.note.model.Collaborator;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;

@Service
@PropertySource(value = "classpath:exception.properties")
public class NoteOperationsService 
{
	@Autowired
	Environment environment; 

	@Autowired
	LabelDao labelDao;

	@Autowired
	NoteDao noteDao;

	@Autowired
	UserServiceFeignClient userServiceFeignClient;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss");

	public Note setProperties(Note note,String ownerId)
	{
		note.setOwnerId(ownerId);
		note.setCreatedDate(DATE_FORMAT.format(new Date()));
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		return note;
	}

	public void removeLabels(String labelId)
	{
		Label label = labelDao.findByLabelId(labelId);
		Set<Note> notes = noteDao.findByLabels_labelId(labelId);

		Set<Label> labels = null;
		for(Note  note : notes)
		{
			notes.remove(note);
			labels = note.getLabels();
			labels.remove(label);
			note.setLabels(labels);
			notes.add(note);
		}
		noteDao.saveAll(notes);
	}

	public void updateLabels(Label label)
	{
		Set<Note> notes = noteDao.findByLabels_labelId(label.getLabelId());
		Set<Label> labels = null;
		for(Note note : notes)
		{
			notes.remove(note);
			labels = note.getLabels();			
			labels.remove(label);
			labels.add(label);
			note.setLabels(labels);
			notes.add(note);
		}
		noteDao.saveAll(notes);
	}

	public Note addCollaborators(Note note,CreateNoteDTO noteDTO)
	{
		User user = null;
		Collaborator collaborator = null;
		Set<Collaborator> collaborators = new LinkedHashSet<>();
		Set<String> collaboratorEmailIds = noteDTO.getCollaboratorEmailIds();
		for(String collaboratorEmail : collaboratorEmailIds)
		{
			user = userServiceFeignClient.getUserByEmail(collaboratorEmail);
			if(user != null)
			{
				collaborator = new Collaborator();
				collaborator.setCollaboratorEmail(user.getEmail());
				collaborator.setCollaboratorName(user.getName());
				collaborator.setCollaboratorProfilePic(user.getProfilePic());
				collaborators.add(collaborator);
			}
		}
		note.setCollaborators(collaborators);
		return note;
	}
	
	public User collaboratorValidations(String noteId,String collaboratorEmail)
	{
		User user = userServiceFeignClient.getUserByEmail(collaboratorEmail);
		if(user == null)
		{
			throw new UserNotFoundException(environment.getProperty("UserNotFoundException"));
		}
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		return user;
	}

	public Note updateCollaborator(Note note,User user)
	{
		Collaborator collaborator = new Collaborator();
		collaborator.setCollaboratorEmail(user.getEmail());
		collaborator.setCollaboratorName(user.getName());
		collaborator.setCollaboratorProfilePic(user.getProfilePic());

		Set<Collaborator> collaborators = note.getCollaborators();
		if(collaborators == null)
		{
			collaborators = new LinkedHashSet<>();
		}
		collaborators.add(collaborator);
		note.setCollaborators(collaborators);
		return note;
	}

	public Note removeCollaborator(Note note,String collaboratorEmail)
	{
		Collaborator collaborator = new Collaborator();
		collaborator.setCollaboratorEmail(collaboratorEmail);

		Set<Collaborator> collaborators = note.getCollaborators();
		if(collaborators == null)
		{
			return note;
		}
		collaborators.remove(collaborator);
		note.setCollaborators(collaborators);
		return note;
	}








}