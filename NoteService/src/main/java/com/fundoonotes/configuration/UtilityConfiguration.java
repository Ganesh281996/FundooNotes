package com.fundoonotes.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fundoonotes.interceptor.LoggerInterceptor;
import com.fundoonotes.interceptor.UserAuthenticationInterceptor;
import com.fundoonotes.utility.JwtTokenService;

@Configuration
public class UtilityConfiguration 
{
	@Bean
	public JwtTokenService getJwtTokenService()
	{
		return new JwtTokenService();
	}
	
	@Bean
	public ModelMapper getmodelMapper()
	{
		return new ModelMapper();
	}
	
	@Bean
	public UserAuthenticationInterceptor userAuthenticationInterceptor()
	{
		return new UserAuthenticationInterceptor();
	}
	
	@Bean
	public LoggerInterceptor loggerInterceptor()
	{
		return new LoggerInterceptor();
	}
}