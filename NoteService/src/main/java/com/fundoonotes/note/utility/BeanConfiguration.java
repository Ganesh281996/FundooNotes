package com.fundoonotes.note.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fundoonotes.note.utility.JwtTokenService;

@Configuration
public class BeanConfiguration 
{
	@Bean
	public JwtTokenService getJwtTokenService()
	{
		return new JwtTokenService();
	}
}