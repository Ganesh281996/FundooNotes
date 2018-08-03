package com.fundoonotes.note.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.api.NoteAPI;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.dto.ResponseNoteDTO;
import com.fundoonotes.note.dto.UpdateNoteDTO;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.service.NoteService;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value=NoteAPI.NOTE)
public class NoteController 
{
	@Autowired
	private NoteService noteService;
	
	@PostMapping(value=NoteAPI.CREATE)
	public ResponseEntity<ResponseNoteDTO> createNote(@RequestBody CreateNoteDTO noteDTO
			,HttpServletRequest request)
	{
		System.out.println(noteDTO);
		ResponseNoteDTO note = noteService.createNote(noteDTO,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(note,HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.UPDATE)
	public ResponseEntity<ResponseNoteDTO> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO
			,HttpServletRequest request)
	{
		ResponseNoteDTO responseNoteDTO = noteService.updateNote(updateNoteDTO,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(responseNoteDTO,HttpStatus.OK);
	}
	
	@DeleteMapping(value=NoteAPI.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable(name="noteId") String noteId
			,HttpServletRequest request)
	{
		noteService.deleteNote(noteId,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Note has been Deleted"),HttpStatus.OK);
	}
	
	@GetMapping(value=NoteAPI.READ)
	public ResponseEntity<List<ResponseNoteDTO>> displayNotes(HttpServletRequest request)
	{
		List<ResponseNoteDTO> responseNoteDTOs = noteService.displayNotes((String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(responseNoteDTOs,HttpStatus.OK);
	}
	
	@GetMapping(value=NoteAPI.READ_ELASTIC_BY_ID)
	public ResponseEntity<List<Map<String, Note>>> displayNotesByElasticSearch(HttpServletRequest request)
	{
		List<Map<String, Note>> notes = noteService.displayNotesByElasticSearch((String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}
	
	@GetMapping(value=NoteAPI.READ_ELASTIC)
	public ResponseEntity<List<Map<String, Note>>> displayNotesBySearch(@PathVariable("search") String search
			,HttpServletRequest request)
	{
		List<Map<String, Note>> notes = noteService.displayNotesBySearch(search);
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.PIN)
	public ResponseEntity<Response> pin(@PathVariable("noteId") String noteId
			,HttpServletRequest request)
	{
		noteService.pin(noteId,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Either Note has been Pinned or Unpinned"),HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.ARCHIEVE)
	public ResponseEntity<Response> archieve(@PathVariable("noteId") String noteId
			,HttpServletRequest request)
	{
		noteService.archieve(noteId,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Either Note has been Archived or removed from Archive"),HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.TRASH)
	public ResponseEntity<Response> trash(@PathVariable("noteId") String noteId
			,HttpServletRequest request)
	{
		noteService.trash(noteId,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Note has been Trashed or Restored"),HttpStatus.OK);
	}
	
	@PutMapping(value=NoteAPI.LABEL)
	public ResponseEntity<Response> addLabel(@PathVariable("noteId") String noteId,
			@PathVariable("labelId") String labelId,HttpServletRequest request)
	{
		noteService.label(noteId, labelId, (String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Label has been Added or Removed"),HttpStatus.OK);
	}
}