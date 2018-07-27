package com.fundoonotes.note.service;

import java.util.List;

import com.fundoonotes.note.model.Label;

public interface LabelService 
{
	Label create(String labelName,String ownerId);	

	Label update(Label label,String ownerId);

	void delete(String labelId,String ownerId);

	List<Label> read(String ownerId);
}