package com.fundoonotes.note.service;

import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.note.utility.Response;

public interface NoteService 
{
	Response createDummyUser(User user);
	
	Response createNote(Note note,String token);
	Response updateNote(Note note,String token);
	Response deleteNote(String noteId,String token);
	Response displayNotes(String token);
	
	Response pin(String noteId,String token);
	Response archieve(String noteId,String token);
	Response trash(String noteId,String token);
	
	Response addLabel(String noteId,String labelId,String token);
}