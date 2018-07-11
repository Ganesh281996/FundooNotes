package com.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fundoonotes.note.utility.JwtTokenService;

@Configuration
public class UtilityConfiguration 
{
	@Bean
	public JwtTokenService getJwtTokenService()
	{
		return new JwtTokenService();
	}
}