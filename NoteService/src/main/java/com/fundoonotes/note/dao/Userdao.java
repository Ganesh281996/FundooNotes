package com.fundoonotes.note.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.note.model.User;

@Repository
public interface Userdao extends MongoRepository<User, String> 
{
	User findByUserId(String userId);
	User findByName(String name);
}