package com.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableOAuth2Sso
//@EnableWebSecurity
//@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
public class NoteServiceApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(NoteServiceApplication.class, args);
	}
}
