package com.fundoonotes.note.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.api.CollaboratorAPI;
import com.fundoonotes.note.service.CollaboratorService;

@RestController
@RequestMapping(value = CollaboratorAPI.COLLABORATOR)
public class CollaboratorController 
{	
	@Autowired
	CollaboratorService collaboratorService;

	@PostMapping(value = CollaboratorAPI.COLLABORATOR_ADD)
	public ResponseEntity<String> addCollaborator(@PathVariable("noteId") String noteId,
			@RequestParam String sharedTo,HttpServletRequest request)
	{
		collaboratorService.addCollaborator(noteId,  sharedTo ,(String)request.getAttribute("ownerId"));
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