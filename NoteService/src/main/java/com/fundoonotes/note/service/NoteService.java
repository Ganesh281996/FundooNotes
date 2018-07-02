package com.fundoonotes.note.service;

import com.fundoonotes.note.model.Note;
import com.fundoonotes.utility.Response;

public interface NoteService 
{
	Response createNote(Note note,String token);
}