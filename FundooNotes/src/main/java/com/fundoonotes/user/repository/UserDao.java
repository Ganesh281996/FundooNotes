package com.fundoonotes.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fundoonotes.user.model.User;

@Repository
public interface UserDao extends MongoRepository<User, String>
{
	public User findBy_id(String _id);
	
	public User findByEmail(String email);
}