package com.fundoonotes.note.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
	@Autowired
	LabelService labelService;
	
	Response response;
	
	@PostMapping(value=LabelAPI.CREATE)
	public ResponseEntity<Label> createLabel(@RequestBody Label label
			,@RequestAttribute("ownerId") String ownerId)
	{
		label = labelService.create(label,ownerId);
		return new ResponseEntity<>(label, HttpStatus.OK);
	}
	
	@PostMapping(value=LabelAPI.UPDATE)
	public ResponseEntity<String> updateLabel(@RequestBody Label label
			,@RequestAttribute("ownerId") String ownerId)
	{
		labelService.update(label,ownerId);
		return new ResponseEntity<>("Label has been Updated", response.getHttpStatus());
	}
	
	@DeleteMapping(value=LabelAPI.DELETE)
	public ResponseEntity<String> deleteLabel(@PathVariable("labelId") String labelId
			,@RequestAttribute("ownerId") String ownerId)
	{
		labelService.delete(labelId,ownerId);
		return new ResponseEntity<>("Label has been Deleted", response.getHttpStatus());
	}
	
	@GetMapping(value=LabelAPI.READ)
	public ResponseEntity<List<Label>> readLabels(@RequestAttribute("ownerId") String ownerId)
	{
		List<Label> labels = labelService.read(ownerId);
		return new ResponseEntity<>(labels, response.getHttpStatus());
	}
}