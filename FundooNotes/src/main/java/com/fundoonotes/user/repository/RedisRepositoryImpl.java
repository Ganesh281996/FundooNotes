//package com.fundoonotes.user.repository;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.fundoonotes.user.model.User;
//
//@Repository
//public class RedisRepositoryImpl implements RedisRepository 
//{
//	private static final String KEY = "Users";
//	
//	@Autowired
//	RedisTemplate<String, Object> redisTemplate;
//	
//	@Override
//	public void save(User user) 
//	{
//		try
//		{
//			redisTemplate.opsForHash().put(KEY, user.get_id(), user);
//		}
//		catch(Exception exception)
//		{
//			exception.printStackTrace();
//		}
//		
//	}
//
//	@Override
//	public Map<Object, Object> getAllUsers() 
//	{
//		return redisTemplate.opsForHash().entries(KEY);
//	}
//}