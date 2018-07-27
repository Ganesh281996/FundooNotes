package com.fundoonotes.note.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.api.CollaboratorAPI;
import com.fundoonotes.note.model.Collaborator;
import com.fundoonotes.note.service.CollaboratorService;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value = CollaboratorAPI.COLLABORATOR)
public class CollaboratorController 
{	
	@Autowired
	CollaboratorService collaboratorService;

	@PostMapping(value = CollaboratorAPI.COLLABORATOR_ADD)
	public ResponseEntity<Response> addCollaborator(@PathVariable("noteId") String noteId,
			@RequestParam String sharedTo,HttpServletRequest request)
	{
		collaboratorService.addCollaborator(noteId,  sharedTo ,(String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Collaborator has been added"), HttpStatus.OK);
	}

	@GetMapping(value = CollaboratorAPI.COLLABORATOR_READ)
	public ResponseEntity<List<Collaborator>> getCollaborators(@PathVariable("noteId") String noteId,
			HttpServletRequest request)
	{
		List<Collaborator> collaborators = collaboratorService.getCollaborators(noteId, (String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(collaborators, HttpStatus.OK);
	}
	
	@DeleteMapping(value = CollaboratorAPI.COLLABORATOR_REMOVE)
	public ResponseEntity<Response> removeCollaborator(@PathVariable("noteId") String noteId,
			@PathVariable("collaboratorId") String collaboratorId,HttpServletRequest request)
	{
		collaboratorService.removeCollaborator(noteId, collaboratorId, (String)request.getAttribute("ownerId"));
		return new ResponseEntity<>(new Response("Collaborator has been added"), HttpStatus.OK);
	}
}