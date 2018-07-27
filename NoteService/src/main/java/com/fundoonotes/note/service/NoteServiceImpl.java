package com.fundoonotes.note.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.ElasticSearchDao;
import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.exception.LabelNotFoundException;
import com.fundoonotes.note.exception.NoteNotFoundException;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.utility.AuthorizeService;
import com.fundoonotes.utility.MapDTOService;
import com.fundoonotes.utility.Response;

@Transactional
@Service
@PropertySource(value = "classpath:exception.properties")
public class NoteServiceImpl implements NoteService 
{
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImpl.class.getName());

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss");

	@Autowired
	Environment environment;
	
	@Autowired
	private NoteDao noteDao;

	@Autowired
	private Userdao userDao;	

	@Autowired
	private LabelDao labelDao;

	@Autowired
	private ElasticSearchDao elasticSearchDao;
	
	@Autowired
	AuthorizeService authorizeService;
	
	@Autowired
	MapDTOService mapDTOService;
	
	@Override
	public Response createDummyUser(User user) 
	{
		userDao.save(user);
		return null;
	}

	@Override
	public Note createNote(CreateNoteDTO createNoteDTO, String ownerId) 
	{
		Note note = mapDTOService.NoteDtoToNote(createNoteDTO);
		
		note.setOwnerId(ownerId);
		note.setCreatedDate(DATE_FORMAT.format(new Date()));
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		note = noteDao.save(note);
		elasticSearchDao.insertNote(note);
		LOGGER.info("Note has been saved");

		return note;
	}

	@Override
	public Note updateNote(Note note, String ownerId) 
	{
		if(!noteDao.existsById(note.getNoteId()))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, note.getNoteId());
		
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		note = noteDao.save(note);
		LOGGER.info("Note has been updated");

		return note;
	}

	@Override
	public void deleteNote(String noteId, String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);
		
		noteDao.deleteById(noteId);
		elasticSearchDao.deleteNote(noteId);
		LOGGER.info("Note has been deleted");
	}

	@Override
	public List<Note> displayNotes(String ownerId) 
	{
		LOGGER.info("Displaying Notes");
		return noteDao.findByOwnerId(ownerId);	
	}
	

	@Override
	public List<Map<String, Note>> displayNotesByElasticSearch(String ownerId) 
	{
		return elasticSearchDao.getNoteByOwnerId(ownerId);
	}
	
	@Override
	public List<Map<String, Note>> displayNotesBySearch(String search) 
	{
		return elasticSearchDao.getNotesBySearch(search);
	}

	@Override
	public void pin(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);

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
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);

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
		authorizeService.authorizeUserWithNote(ownerId, noteId);

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
		authorizeService.authorizeUserWithNote(ownerId, noteId);

		Note note = noteDao.findByNoteId(noteId);
		Label label = labelDao.findByLabelId(labelId);

		List<String> notes = null;
		List<Label> labels = null;

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

		if(labels.contains(label) && notes.contains(labelId))
		{
			labels.remove(label);
			notes.remove(labelId);
			LOGGER.info("Label has been removed from Note");
		}
		else
		{
			labels.add(label);
			notes.add(note.getNoteId());
			LOGGER.info("Label has been added to Note");
		}

		note.setLabels(labels);
		label.setNotes(notes);

		noteDao.save(note);
		labelDao.save(label);
	}
}