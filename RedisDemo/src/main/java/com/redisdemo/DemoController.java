package com.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController 
{
	@Autowired
	RedisRepository redisRepository;
	
	@PostMapping(value = "/demo")
	public Person demo(@RequestBody Person person)
	{
		System.out.println(person);
		Person person2 = redisRepository.save(person);
		return person2;
	}
	
	@PostMapping(value = "/demoget")
	public Iterable<Person> demoGet()
	{
		System.out.println(redisRepository.findAll());
		return redisRepository.findAll();
	}
}