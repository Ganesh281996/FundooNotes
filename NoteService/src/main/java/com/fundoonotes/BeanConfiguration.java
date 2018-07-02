package com.fundoonotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fundoonotes.utility.JwtTokenService;

@Configuration
public class BeanConfiguration 
{
	@Bean
	public JwtTokenService getJwtTokenService()
	{
		return new JwtTokenService();
	}
}