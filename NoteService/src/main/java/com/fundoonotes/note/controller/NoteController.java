package com.fundoonotes.note.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.service.NoteService;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value="/note")
public class NoteController 
{
	@Autowired
	NoteService noteService;
	
	Response response;
	
	@PostMapping(value="/create")
	public ResponseEntity<Response> createNote(@RequestBody Note note,HttpServletRequest httpServletRequest)
	{
		response = noteService.createNote(note, httpServletRequest.getHeader("token"));
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
}