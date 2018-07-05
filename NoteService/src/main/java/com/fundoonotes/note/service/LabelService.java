package com.fundoonotes.note.service;

import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.utility.Response;

public interface LabelService 
{
	Response create(Label label,String token);
	Response update(Label label,String token);
	Response delete(String labelId,String token);
	Response read(String token);
}