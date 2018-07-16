package com.fundoonotes.note.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.dto.NoteDTO;
import com.fundoonotes.note.exception.LabelNotFoundException;
import com.fundoonotes.note.exception.NoteNotFoundException;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@Transactional
@Service
public class NoteServiceImpl implements NoteService 
{
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImpl.class.getName());

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss");

	@Autowired
	NoteDao noteDao;

	@Autowired
	Userdao userDao;	

	@Autowired
	LabelDao labelDao;

	@Autowired
	JwtTokenService jwtTokenService;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Response createDummyUser(User user) 
	{
		userDao.save(user);
		return null;
	}

	@Override
	public Note createNote(NoteDTO createNoteDTO, String ownerId) 
	{
		Note note = modelMapper.map(createNoteDTO, Note.class);

		note.setOwnerId(ownerId);
		note.setCreatedDate(DATE_FORMAT.format(new Date()));
		note = noteDao.save(note);
		LOGGER.info("Note has been saved");

		return note;
	}

	@Override
	public Note updateNote(Note note, String ownerId) 
	{
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		note = noteDao.save(note);
		LOGGER.info("Note has been updated");

		return note;
	}

	@Override
	public void deleteNote(String noteId, String ownerId) 
	{		
		noteDao.deleteById(noteId);
		LOGGER.info("Note has been deleted");
	}

	@Override
	public List<Note> displayNotes(String ownerId) 
	{
		LOGGER.info("Displaying Notes");
		return noteDao.findByOwnerId(ownerId);	
	}

	@Override
	public void pin(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException("Note with NoteID = "+noteId+" was not Found");
		}

		Note note = noteDao.findByNoteId(noteId);
		if (note.isPinned())
		{
			note.setPinned(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been UnPinned");
		}
		else
		{
			note.setPinned(true);
			note.setArchieved(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been Pinned");
		}
	}

	@Override
	public void archieve(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException("Note with NoteID = "+noteId+" was not Found");
		}

		Note note = noteDao.findByNoteId(noteId);
		if (note.isArchieved())
		{
			note.setArchieved(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been removed from Archieve");
		}
		else
		{
			note.setArchieved(true);
			note.setPinned(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been added to Archieve");
		}
	}

	@Override
	public void trash(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException("Note with NoteID = "+noteId+" was not Found");
		}

		Note note = noteDao.findByNoteId(noteId);
		if (note.isInTrash())
		{
			note.setInTrash(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been restored from trash");
		}
		else
		{
			note.setInTrash(true);
			note.setPinned(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been added to trash");
		}
	}

	@Override
	public void label(String noteId, String labelId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException("Note with NoteID = "+noteId+" was not Found");
		}
		if(!labelDao.existsById(labelId))
		{
			throw new LabelNotFoundException("Label with LabelID = "+labelId+" was not Found");
		}

		Note note = noteDao.findByNoteId(noteId);
		Label label = labelDao.findByLabelId(labelId);

		List<String> notes = null;
		List<String> labels = null;

		if(note.getLabels() == null || label.getNotes() == null )
		{
			labels = new ArrayList<>();
			notes = new ArrayList<>();
		}
		else
		{
			labels = note.getLabels();
			notes = label.getNotes();
		}

		if(labels.contains(noteId) && notes.contains(labelId))
		{
			labels.remove(noteId);
			notes.remove(labelId);
			LOGGER.info("Label has been removed from Note");
		}
		else
		{
			labels.add(label.getLabelId());
			notes.add(note.getNoteId());
			LOGGER.info("Label has been added to Note");
		}

		note.setLabels(labels);
		label.setNotes(notes);

		noteDao.save(note);
		labelDao.save(label);
	}
}