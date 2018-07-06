package com.fundoonotes.note.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.note.service.NoteService;
import com.fundoonotes.note.utility.NoteAPI;
import com.fundoonotes.note.utility.JwtTokenService;
import com.fundoonotes.note.utility.Response;

@RestController
@RequestMapping(value=NoteAPI.NOTE)
public class NoteController 
{
	private static final Logger LOGGER = Logger.getLogger(NoteController.class.getName());
	
	@Autowired
	NoteService noteService;
	
	static
	{
		JwtTokenService jwtTokenService=new JwtTokenService();
		String token = jwtTokenService.getJwtToken("5b3c575012b32521affe1abd");
		System.out.println(token);
	}
	
	Response response;
	
	@PostMapping(value="/dummyuser")
	public String createDummyUser(@RequestBody User user)
	{
		noteService.createDummyUser(user);
		return "success";
	}
	
	@PostMapping(value=NoteAPI.CREATE)
	public ResponseEntity<Response> createNote(@RequestBody Note note,HttpServletRequest request)
	{
		LOGGER.info("Post Request for creating Note in URL : "+NoteAPI.CREATE);
		LOGGER.info("PARAMETERS : note = "+note);
		
		response = noteService.createNote(note, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value=NoteAPI.UPDATE)
	public ResponseEntity<Response> updateNote(@RequestBody Note note,HttpServletRequest request)
	{
		LOGGER.info("Put Request for updating Note in URL : "+NoteAPI.UPDATE);
		LOGGER.info("PARAMETERS : note = "+note);
		
		response = noteService.updateNote(note, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@DeleteMapping(value=NoteAPI.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable(name="noteId") String noteId,HttpServletRequest request)
	{
		LOGGER.info("Delete Request for deleting Note in URl "+NoteAPI.DELETE);
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.deleteNote(noteId, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@GetMapping(value=NoteAPI.READ)
	public ResponseEntity<Response> displayNotes(HttpServletRequest request)
	{
		LOGGER.info("Get Request for displaying Note in URL : "+NoteAPI.READ);
		
		response = noteService.displayNotes(request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value=NoteAPI.PIN)
	public ResponseEntity<Response> pin(@PathVariable("noteId") String noteId,HttpServletRequest request)
	{
		LOGGER.info("Put Request to pin Note in URL : "+NoteAPI.PIN);
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.pin(noteId,request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value=NoteAPI.ARCHIEVE)
	public ResponseEntity<Response> archieve(@PathVariable("noteId") String noteId,HttpServletRequest request)
	{
		LOGGER.info("Put Request for archieving Note in URL : "+NoteAPI.ARCHIEVE);
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.archieve(noteId,request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value=NoteAPI.TRASH)
	public ResponseEntity<Response> trash(@PathVariable("noteId") String noteId,HttpServletRequest request)
	{
		LOGGER.info("Put Request to trash or restore Note in URL : "+NoteAPI.TRASH);
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.trash(noteId,request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	@PutMapping(value=NoteAPI.ADD_LABEL)
	public ResponseEntity<Response> addLabel(@PathVariable("noteId") String noteId,
			@PathVariable("labelId") String labelId,HttpServletRequest request)
	{
		LOGGER.info("Put Request for adding or removing Label to Note in URL : "+NoteAPI.ADD_LABEL);
		LOGGER.info("PARAMETERS : noteId = "+noteId+" labelId = "+labelId);
		
		response = noteService.addLabel(noteId, labelId, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
}