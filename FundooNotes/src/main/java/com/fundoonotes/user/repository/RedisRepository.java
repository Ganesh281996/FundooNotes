package com.fundoonotes.user.repository;

import java.util.Map;

import com.fundoonotes.user.model.User;

public interface RedisRepository 
{
	void save(User user);
	
	Map<Object, Object> getAllUsers();
}