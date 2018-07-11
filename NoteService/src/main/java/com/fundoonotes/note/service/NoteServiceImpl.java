package com.fundoonotes.note.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
import com.fundoonotes.note.utility.JwtTokenService;
import com.fundoonotes.note.utility.Response;

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
		
		note.setUser(userDao.findByUserId(userId));
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
		
		response.setData(noteDao.findByUser_UserId(userId));
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
	public Response addLabel(String noteId, String labelId,String token) 
	{
		Response response = new Response();
		
		Label label = null;
		Note note = null;
		List<Note> notes = null;
		List<Label> labels = null;
		
		jwtTokenService.verifyToken(token);
		LOGGER.info("Token has been verified");
		
		note = noteDao.findByNoteId(noteId);
		label = labelDao.findByLabelId(labelId);
		
		if(note.getLabels() == null || label.getNotes() == null )
		{
			labels = new LinkedList<>();
			notes = new LinkedList<>();
		}
		else
		{
			labels = note.getLabels();
			notes = label.getNotes();
		}
		labels.add(label);
		notes.add(note);
		
		note.setLabels(labels);
		label.setNotes(notes);
		
		note = noteDao.save(note);
		label = labelDao.save(label);
		LOGGER.info("Label has been added to Note");
		
		response.setMessage("Label has been added to Note");
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
}