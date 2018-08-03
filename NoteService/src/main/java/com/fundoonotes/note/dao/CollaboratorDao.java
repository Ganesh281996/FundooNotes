//package com.fundoonotes.note.dao;
//
//import java.util.List;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import com.fundoonotes.note.model.Collaborator;
//
//@Repository
//public interface CollaboratorDao extends MongoRepository<Collaborator, String> 
//{
//	void deleteByCollaboratorId(String collaboratorId);
//	
//	List<Collaborator> findByNoteId(String noteId);
//}