package com.fundoonotes.utility;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.Note;

@Service
public class MapDTOService 
{
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	NoteDao noteDao;
	
	@Autowired
	LabelDao labelDao;
	
	public Note noteDtoToNote(CreateNoteDTO noteDTO)
	{
		Note note = modelMapper.map(noteDTO, Note.class);
		if(noteDTO.getLabels() == null)
		{
			return note;
		}
		List<String> labelIds = noteDTO.getLabels();
		List<Label> labels = new ArrayList<>();
		for(int i=0 ; i<labelIds.size() ; i++)
		{
			labels.add(labelDao.findByLabelId(labelIds.get(i)));
		}
		note.setLabels(labels);
		return note;
	}
}