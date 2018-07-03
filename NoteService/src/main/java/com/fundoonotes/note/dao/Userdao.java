package com.fundoonotes.note.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.note.model.User;

@Repository
public interface Userdao extends MongoRepository<User, String> 
{
	User findBy_id(String _id);
	User findByName(String name);
}