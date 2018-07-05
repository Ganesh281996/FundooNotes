package com.fundoonotes.note.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.note.model.Note;

@Repository
public interface NoteDao extends MongoRepository<Note, String> 
{	
	List<Note> findByUser_UserId(String userId);
	Note findByNoteId(String noteId);
	List<Note> findByLabel_LabelId(String labelId);
}