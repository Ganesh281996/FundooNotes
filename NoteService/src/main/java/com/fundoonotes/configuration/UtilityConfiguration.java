package com.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fundoonotes.utility.JwtTokenService;

@Configuration
public class UtilityConfiguration 
{
	@Bean
	public JwtTokenService getJwtTokenService()
	{
		return new JwtTokenService();
	}
}