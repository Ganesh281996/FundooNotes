package com.fundoonotes.note.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.note.model.Note;

@Repository
public interface NoteDao extends MongoRepository<Note, String> 
{	
	List<Note> findByOwnerId(String ownerId);

	Note findByNoteId(String noteId);

	Set<Note> findByLabels_labelId(String labelId);
}