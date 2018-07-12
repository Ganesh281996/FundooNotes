package com.fundoonotes.note.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<Response> addCollaborator(@PathVariable("noteId") String noteId,
					@RequestParam String sharedTo,HttpServletRequest request)
	{
		Response response = collaboratorService.addCollaborator(noteId, request.getHeader("token"), sharedTo);
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
}