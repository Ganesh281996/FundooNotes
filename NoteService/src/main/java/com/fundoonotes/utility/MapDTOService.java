package com.fundoonotes.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.dto.ResponseNoteDTO;
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
		if(noteDTO.getLabelNames() == null || noteDTO.getLabelNames().size() == 0)
		{
			return note;
		}
		Set<String> labelNames = noteDTO.getLabelNames();
		Set<Label> labels = new HashSet<>();
		Label label = null;
		for(String labelName : labelNames)
		{
			label = labelDao.findByLabelName(labelName);
			if(label != null)
			{
				labels.add(label);
			}
		}
		note.setLabels(labels);
		return note;
	}

	public List<ResponseNoteDTO> noteDtoToResponseNoteDto(List<Note> notes)
	{
		List<ResponseNoteDTO> responseNoteDTOs = new ArrayList<>();

		for(int i=0 ; i<notes.size() ; i++)
		{
			responseNoteDTOs.add(modelMapper.map(notes.get(i), ResponseNoteDTO.class));
		}
		return responseNoteDTOs;
	}
}