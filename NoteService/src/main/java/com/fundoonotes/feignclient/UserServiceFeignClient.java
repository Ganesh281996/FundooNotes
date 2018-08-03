package com.fundoonotes.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fundoonotes.note.model.User;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient 
{
	@GetMapping(value="/user/getuserbyemail/{email}")
	User getUserByEmail(@PathVariable("email") String email);
}