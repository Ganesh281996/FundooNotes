package com.fundoonotes.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fundoonotes.interceptor.LoggerInterceptor;

public class InterceptorConfiguration implements WebMvcConfigurer
{
	@Autowired
	LoggerInterceptor loggerInterceptor; 
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) 
	{
		registry.addInterceptor(loggerInterceptor);
	}
}