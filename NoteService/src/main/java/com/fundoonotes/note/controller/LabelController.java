package com.fundoonotes.note.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.service.LabelService;
import com.fundoonotes.utility.LabelAPI;
import com.fundoonotes.utility.Response;

@RestController
@RequestMapping(value=LabelAPI.LABEL)
public class LabelController 
{
	private static final Logger LOGGER = Logger.getLogger(LabelController.class.getName());
	
	@Autowired
	LabelService labelService;
	
	Response response;
	
	@PostMapping(value=LabelAPI.CREATE)
	public ResponseEntity<Response> createLabel(@RequestBody Label label,HttpServletRequest request)
	{
		LOGGER.info("Post Request for creating Note in URL : "+LabelAPI.CREATE);
		LOGGER.info("PARAMETERS : Label = "+label);
		
		response = labelService.create(label,request.getHeader("token"));
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@PostMapping(value=LabelAPI.UPDATE)
	public ResponseEntity<Response> updateLabel(@RequestBody Label label,HttpServletRequest request)
	{
		LOGGER.info("Post Request for creating Note in URL : "+LabelAPI.CREATE);
		LOGGER.info("PARAMETERS : Label = "+label);
		
		response = labelService.update(label,request.getHeader("token"));
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@DeleteMapping(value=LabelAPI.DELETE)
	public ResponseEntity<Response> deleteLabel(@PathVariable("labelId") String labelId,HttpServletRequest request)
	{
		LOGGER.info("Post Request for creating Note in URL : "+LabelAPI.CREATE);
		LOGGER.info("PARAMETERS : LabelId = "+labelId);
		
		response = labelService.delete(labelId,request.getHeader("token"));
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
	
	@GetMapping(value=LabelAPI.READ)
	public ResponseEntity<Response> readLabels(HttpServletRequest request)
	{
		LOGGER.info("Post Request for creating Note in URL : "+LabelAPI.CREATE);
		
		response = labelService.read(request.getHeader("token"));
		return new ResponseEntity<Response>(response, response.getHttpStatus());
	}
}