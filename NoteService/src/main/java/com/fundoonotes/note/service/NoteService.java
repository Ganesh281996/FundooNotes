package com.fundoonotes.note.service;

import java.util.List;
import java.util.Map;

import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.utility.Response;

public interface NoteService 
{
	Response createDummyUser(User user);
	
	Note createNote(CreateNoteDTO createNoteDTO,String ownerId);
	Note updateNote(Note note,String ownerId);
	void deleteNote(String noteId,String ownerId);
	List<Note> displayNotes(String ownerId);
	
	List<Map<String, Note>> displayNotesByElasticSearch(String ownerId);
	List<Map<String, Note>> displayNotesBySearch(String search);
	
	void pin(String noteId,String ownerId);
	void archieve(String noteId,String ownerId);
	void trash(String noteId,String ownerId);
	
	void label(String noteId,String labelId,String ownerId);
}