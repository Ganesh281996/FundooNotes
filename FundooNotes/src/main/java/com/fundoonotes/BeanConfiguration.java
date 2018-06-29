package com.fundoonotes;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fundoonotes.user.model.User;
import com.fundoonotes.utility.EmailService;
import com.fundoonotes.utility.JwtTokenService;

@Configuration
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
	
	@Bean
	 public Exchange eventExchange() {
	   return new TopicExchange("eventExchange");
	 }
	
	@Bean
	public JedisConnectionFactory getJedisConnectionFactory()
	{
		return new JedisConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, User> getRedisTemplate()
	{
		RedisTemplate<String, User> redisTemplate=new RedisTemplate<>();
		redisTemplate.setConnectionFactory(getJedisConnectionFactory());
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}
}