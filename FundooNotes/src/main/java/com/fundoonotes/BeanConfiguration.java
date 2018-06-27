package com.fundoonotes;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fundoonotes.user.model.User;
import com.fundoonotes.utility.EmailService;
import com.fundoonotes.utility.JwtTokenService;

@Component
public class BeanConfiguration 
{	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtTokenService getJwtTokenService()
	{
		return new JwtTokenService();
	}
	
	@Bean
	public EmailService getEmailService()
	{
		return new EmailService();
	}
	
//	@Bean
//	public RedisTemplate<String, User> getRedisTemplate()
//	{
//		RedisTemplate<String, User> redisTemplate=new RedisTemplate<>();
//		redisTemplate.setConnectionFactory();
////		redisTemplate.setEnableTransactionSupport(true);
//		return redisTemplate;
//	}
}