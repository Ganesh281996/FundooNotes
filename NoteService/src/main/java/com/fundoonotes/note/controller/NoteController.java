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
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value="/note")
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
	
	@PostMapping(value="/create")
	public ResponseEntity<Response> createNote(@RequestBody Note note,HttpServletRequest request)
	{
		LOGGER.info("Post Request for creating Note");
		LOGGER.info("PARAMETERS : note = "+note);
		
		response = noteService.createNote(note, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<Response> updateNote(@RequestBody Note note,HttpServletRequest request)
	{
		LOGGER.info("Put Request for updating Note");
		LOGGER.info("PARAMETERS : note = "+note);
		
		response = noteService.updateNote(note, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@DeleteMapping(value="/delete/{noteId}")
	public ResponseEntity<Response> deleteNote(@PathVariable(name="noteId") String noteId,HttpServletRequest request)
	{
		LOGGER.info("Delete Request for deleting Note");
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.deleteNote(noteId, request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@GetMapping(value="/read")
	public ResponseEntity<Response> displayNotes(HttpServletRequest request)
	{
		LOGGER.info("Get Request for displaying Note");
		
		response = noteService.displayNotes(request.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value="/pin/{noteId}")
	public ResponseEntity<Response> pin(@PathVariable("noteId") String noteId)
	{
		LOGGER.info("Put Request to pin Note");
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.pin(noteId);
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value="/archieve/{noteId}")
	public ResponseEntity<Response> archieve(@PathVariable("noteId") String noteId)
	{
		LOGGER.info("Put Request for archieving Note");
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.archieve(noteId);
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	
	@PutMapping(value="/trash/{noteId}")
	public ResponseEntity<Response> trash(@PathVariable("noteId") String noteId)
	{
		LOGGER.info("Put Request to trash or restore Note");
		LOGGER.info("PARAMETERS : noteId = "+noteId);
		
		response = noteService.trash(noteId);
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
}