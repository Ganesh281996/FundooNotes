package com.fundoonotes.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fundoonotes.interceptor.LoggerInterceptor;
import com.fundoonotes.interceptor.UserAuthenticationInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer
{
	@Autowired
	UserAuthenticationInterceptor userAuthenticationInterceptor; 
	
	@Autowired
	LoggerInterceptor loggerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) 
	{
		registry.addInterceptor(userAuthenticationInterceptor)
		.addPathPatterns("/note/**");
		
		registry.addInterceptor(loggerInterceptor);		
	}
}