package com.fundoonotes.note.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.note.dto.NoteDTO;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.note.service.NoteService;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.NoteAPI;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value=NoteAPI.NOTE)
public class NoteController 
{
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
	public ResponseEntity<Note> createNote(@RequestBody NoteDTO noteDTO
			,@RequestAttribute("ownerId") String ownerId)
	{
		Note note = noteService.createNote(noteDTO,ownerId);
		return new ResponseEntity<>(note,HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.UPDATE)
	public ResponseEntity<Note> updateNote(@RequestBody Note note
			,@RequestAttribute("ownerId") String ownerId)
	{
		note = noteService.updateNote(note,ownerId);
		return new ResponseEntity<>(note,HttpStatus.OK);
	}
	
	@DeleteMapping(value=NoteAPI.DELETE)
	public ResponseEntity<String> deleteNote(@PathVariable(name="noteId") String noteId
			,@RequestAttribute("ownerId") String ownerId)
	{
		noteService.deleteNote(noteId,ownerId);
		return new ResponseEntity<>("Note has been Deleted",HttpStatus.OK);
	}
	
	@GetMapping(value=NoteAPI.READ)
	public ResponseEntity<List<Note>> displayNotes(@RequestAttribute("ownerId") String ownerId)
	{
		List<Note> notes = noteService.displayNotes(ownerId);
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.PIN)
	public ResponseEntity<String> pin(@PathVariable("noteId") String noteId
			,@RequestAttribute("ownerId") String ownerId)
	{
		noteService.pin(noteId,ownerId);
		return new ResponseEntity<>("Either Note has been Pinned or Unpinned",HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.ARCHIEVE)
	public ResponseEntity<String> archieve(@PathVariable("noteId") String noteId
			,@RequestAttribute("ownerId") String ownerId)
	{
		noteService.archieve(noteId,ownerId);
		return new ResponseEntity<>("Either Note has been Archived or removed from Archive",HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.TRASH)
	public ResponseEntity<String> trash(@PathVariable("noteId") String noteId
			,@RequestAttribute("ownerId") String ownerId)
	{
		noteService.trash(noteId,ownerId);
		return new ResponseEntity<>("Note has been Trashed or Restored",HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.LABEL)
	public ResponseEntity<String> addLabel(@PathVariable("noteId") String noteId,
			@PathVariable("labelId") String labelId,@RequestAttribute("ownerId") String ownerId)
	{
		noteService.label(noteId, labelId, ownerId);
		return new ResponseEntity<>("Label has been Added or Removed",HttpStatus.OK);
	}
}