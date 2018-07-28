package com.fundoonotes.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.Note;

@Service
public class NoteOperationsService 
{
	@Autowired
	private static LabelDao labelDao;
	
	@Autowired
	private static NoteDao noteDao;
	
	public void addLabels(Note note,List<String> labelNames)
	{
		Label label = null;
		
		for(String labelName : labelNames)
		{
			if(!labelDao.existsByLabelName(labelName))
			{
				labelNames.remove(labelName);
			}
		}
		note = noteDao.save(note);
		for(String labelName : labelNames)
		{
			label = labelDao.findByLabelName(labelName);
//			label.getNotes().add(e);
		}
	}
}