package com.fundoonotes.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

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
	LabelDao labelDao;
	
	@Autowired
	NoteDao noteDao;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss");
		
	public Note setProperties(Note note,String ownerId)
	{
		note.setOwnerId(ownerId);
		note.setCreatedDate(DATE_FORMAT.format(new Date()));
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		return note;
	}
	
	public void removeLabels(String labelId)
	{
		Label label = labelDao.findByLabelId(labelId);
		Set<Note> notes = noteDao.findByLabels_labelId(labelId);
		
		Set<Label> labels = null;
		for(Note  note : notes)
		{
			notes.remove(note);
			labels = note.getLabels();
			labels.remove(label);
			note.setLabels(labels);
			notes.add(note);
		}
		noteDao.saveAll(notes);
	}
	
	public void updateLabels(Label label)
	{
		Set<Note> notes = noteDao.findByLabels_labelId(label.getLabelId());
		Set<Label> labels = null;
		for(Note note : notes)
		{
			notes.remove(note);
			labels = note.getLabels();			
			labels.remove(label);
			labels.add(label);
			note.setLabels(labels);
			notes.add(note);
		}
		noteDao.saveAll(notes);
	}
	
//	public void addLabels(Note note,List<String> labelNames)
//	{
//		Label label = null;
//		
//		for(String labelName : labelNames)
//		{
//			if(!labelDao.existsByLabelName(labelName))
//			{
//				labelNames.remove(labelName);
//			}
//		}
//		note = noteDao.save(note);
//		for(String labelName : labelNames)
//		{
//			label = labelDao.findByLabelName(labelName);
////			label.getNotes().add(e);
//		}
//	}
}