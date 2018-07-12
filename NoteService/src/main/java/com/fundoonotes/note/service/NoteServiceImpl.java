package com.fundoonotes.note.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
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

	@Override
	public Response createDummyUser(User user) 
	{
		userDao.save(user);
		return null;
	}

	@Override
	public Response createNote(Note note, String token) 
	{
		Response response = new Response();
		
		String userId = jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		note.setOwnerId(userId);
		note.setCreatedDate(DATE_FORMAT.format(new Date()));
		note = noteDao.save(note);
		LOGGER.info("Note has been saved");
		
		response.setMessage("Note has been saved");
		response.setHttpStatus(HttpStatus.OK);
		response.setData(note);
		return response;
	}

	@Override
	public Response updateNote(Note note, String token) 
	{
		Response response = new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		noteDao.save(note);
		LOGGER.info("Note has been updated");
		
		response.setMessage("Note has been updated");
		response.setHttpStatus(HttpStatus.OK);
		response.setData(note);
		return response;
	}

	@Override
	public Response deleteNote(String noteId, String token) 
	{
		Response response=new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		noteDao.deleteById(noteId);
		LOGGER.info("Note has been deleted");
		
		response.setMessage("Note has been deleted");
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@Override
	public Response displayNotes(String token) 
	{
		Response response=new Response();
		
		String userId = jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		response.setData(noteDao.findByOwnerId(userId));
		response.setMessage("Displaying Notes");
		LOGGER.info("Displaying Notes");
		response.setHttpStatus(HttpStatus.FOUND);
		return response;
	}

	@Override
	public Response pin(String noteId,String token) 
	{
		Response response = new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		Note note = noteDao.findByNoteId(noteId);
		if (note.isPinned())
		{
			note.setPinned(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been UnPinned");
			
			response.setMessage("Note has been UnPinned");
			response.setData(note);
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		else
		{
			note.setPinned(true);
			note.setArchieved(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been Pinned");
			
			response.setMessage("Note has been Pinned");
			response.setData(note);
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
	}

	@Override
	public Response archieve(String noteId,String token) 
	{
		Response response = new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		Note note = noteDao.findByNoteId(noteId);
		if (note.isArchieved())
		{
			note.setArchieved(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been removed from Archieve");
			
			response.setMessage("Note has been removed from Archieve");
			response.setData(note);
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		else
		{
			note.setArchieved(true);
			note.setPinned(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been added to Archieve");
			
			response.setMessage("Note has been added to Archieve");
			response.setData(note);
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
	}

	@Override
	public Response trash(String noteId,String token) 
	{
		Response response = new Response();
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		Note note = noteDao.findByNoteId(noteId);
		if (note.isInTrash())
		{
			note.setInTrash(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been restored from trash");
			
			response.setMessage("Note has been restored from trash");
			response.setData(note);
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
		else
		{
			note.setInTrash(true);
			note.setPinned(false);
			note = noteDao.save(note);
			LOGGER.info("Note has been added to trash");
			
			response.setMessage("Note has been added to trash");
			response.setData(note);
			response.setHttpStatus(HttpStatus.OK);
			return response;
		}
	}

	@Override
	public Response label(String noteId, String labelId,String token) 
	{
		Response response = new Response();
		
		Label label = null;
		Note note = null;
		List<String> notes = null;
		List<String> labels = null;
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		note = noteDao.findByNoteId(noteId);
		label = labelDao.findByLabelId(labelId);
		
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
			response.setMessage("Label has been removed from Note");
		}
		else
		{
			labels.add(label.getLabelId());
			notes.add(note.getNoteId());
			LOGGER.info("Label has been added to Note");
			response.setMessage("Label has been added to Note");
		}
		
		note.setLabels(labels);
		label.setNotes(notes);
		
		note = noteDao.save(note);
		label = labelDao.save(label);
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
}