package com.fundoonotes.note.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.note.model.Label;

@Repository
public interface LabelDao extends MongoRepository<Label, String> 
{
	List<Label> findByUserId(String userId);

	Label findByLabelId(String labelId);
	
	Label findByLabelName(String labelName);
	
	boolean existsByLabelName(String labelName);
}