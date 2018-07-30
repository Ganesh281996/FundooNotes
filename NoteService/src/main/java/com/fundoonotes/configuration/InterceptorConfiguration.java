package com.fundoonotes.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fundoonotes.interceptor.LoggerInterceptor;
import com.fundoonotes.interceptor.UserAuthenticationInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer
{
	@Autowired
	UserAuthenticationInterceptor userAuthenticationInterceptor; 
	
	@Autowired
	LoggerInterceptor loggerInterceptor;
	
	@Autowired
	LocaleChangeInterceptor localeChangeInterceptor; 
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) 
	{
		registry.addInterceptor(userAuthenticationInterceptor)
		.addPathPatterns("/note/*");
		
		registry.addInterceptor(loggerInterceptor);
		
		registry.addInterceptor(localeChangeInterceptor);
	}
}