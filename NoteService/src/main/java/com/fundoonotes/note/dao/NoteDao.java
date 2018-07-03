package com.fundoonotes.note.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.note.model.Note;

@Repository
public interface NoteDao extends MongoRepository<Note, String> 
{
	int deleteBy_id(String _id);
	
	List<Note> findByUser(String _id);
}