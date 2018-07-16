package com.fundoonotes.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.service.CollaboratorService;
import com.fundoonotes.utility.CollaboratorAPI;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value = CollaboratorAPI.COLLABORATOR)
public class CollaboratorController 
{	
	@Autowired
	CollaboratorService collaboratorService;

	@PostMapping(value = CollaboratorAPI.COLLABORATOR_ADD)
	public ResponseEntity<String> addCollaborator(@PathVariable("noteId") String noteId,
			@RequestParam String sharedTo,@RequestAttribute("ownerId") String ownerId)
	{
		collaboratorService.addCollaborator(noteId,  sharedTo ,ownerId);
		return new ResponseEntity<>("Collaborator has been added", HttpStatus.OK);
	}

//	@GetMapping(value=CollaboratorAPI.COLLABORATOR_READ)
//	public ResponseEntity<Response> createLabel(@RequestBody Label label
//			,@RequestAttribute("ownerId") String ownerId)
//	{
//		Response response = null;
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
}