package com.redisdemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<Person , String> 
{
	
}