package com.fundoonotes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.fundoonotes.note.dao.ElasticSearchDao;
import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.service.NoteServiceImpl;
import com.fundoonotes.utility.AuthorizeService;
import com.fundoonotes.utility.MapDTOService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceApplicationTests 
{
	@Mock
	Environment environment;
	
	@Mock
	NoteDao noteDao;
	
	@Mock
	LabelDao labelDao;
	
	@Mock
	ElasticSearchDao elasticSearchDao;
	
	@Mock
	MapDTOService mapDTOService;
	
	@Mock
	AuthorizeService authorizeService; 
	
	@Mock
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss");  
	
	@InjectMocks
	NoteServiceImpl noteServiceImpl;
	
	@Test
	public void createNote()
	{
		Note note = new Note();
		note.setTitle("title");
		
		CreateNoteDTO noteDTO = new CreateNoteDTO();
		noteDTO.setTitle("title");
		when(mapDTOService.NoteDtoToNote(noteDTO)).thenReturn(note);
		when(noteDao.save(note)).thenReturn(note);
		
		Note note2 = noteServiceImpl.createNote(noteDTO, "1");
		assertEquals(note, note2);
	}
	
	@Test
	public void displayNotes()
	{
		Note note = new Note();
		List<Note> notes = new ArrayList<>();
		notes.add(note);
		when(noteDao.findByOwnerId("1")).thenReturn(notes);
		
		List<Note> notes2 = noteServiceImpl.displayNotes("1");
		assertEquals(notes, notes2);
	}
	
	@Test
	public void updateNote()
	{
		Note note = new Note();
		note.setNoteId("1");
		note.setTitle("title");
		when(noteDao.existsById("1")).thenReturn(true);
		when(noteDao.save(note)).thenReturn(note);
		Note note2 = noteServiceImpl.updateNote(note, "1");
		
		assertEquals(note, note2);
	}
	
	@Test
	public void deleteNote()
	{
		Note note = new Note();
		note.setNoteId("1");
		note.setTitle("title");
		when(noteDao.existsById("1")).thenReturn(true);
		
		noteServiceImpl.deleteNote("1", "1");
		verify(noteDao).deleteById("1");
		verify(elasticSearchDao).deleteNote("1");
	}
	
	@Test
	public void pinNote()
	{
		Note note = new Note();
		note.setNoteId("1");
		note.setTitle("title");
		note.setPinned(false);
		when(noteDao.existsById("1")).thenReturn(true);
		when(noteDao.findByNoteId("1")).thenReturn(note);
		
		noteServiceImpl.pin("1", "1");
		verify(noteDao).findByNoteId("1");
	}
	
	@Test
	public void archieveNote()
	{
		Note note = new Note();
		note.setNoteId("1");
		note.setTitle("title");
		note.setArchieved(false);
		when(noteDao.existsById("1")).thenReturn(true);
		when(noteDao.findByNoteId("1")).thenReturn(note);
		
		noteServiceImpl.pin("1", "1");
		verify(noteDao).findByNoteId("1");
	}
	
	@Test
	public void trashNote()
	{
		Note note = new Note();
		note.setNoteId("1");
		note.setTitle("title");
		note.setInTrash(false);
		when(noteDao.existsById("1")).thenReturn(true);
		when(noteDao.findByNoteId("1")).thenReturn(note);
		
		noteServiceImpl.pin("1", "1");
		verify(noteDao).findByNoteId("1");
	}
	
	
	
	
}