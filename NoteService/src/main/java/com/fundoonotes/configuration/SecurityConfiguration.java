package com.fundoonotes.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter 
{
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
		.csrf()
		.disable()
		.antMatcher("/**")
		.authorizeRequests()
		.antMatchers("/")
		.permitAll()
		.anyRequest()
		.authenticated();
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.antMatcher("/**")
//		.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
//		.authorizeRequests()
//			.antMatchers("/", "/connect**", "/webjars/**")
//			.permitAll()
//		.anyRequest()
//			.authenticated()
//		.and()
//			.logout()
//		    .logoutSuccessUrl("/").permitAll().and().csrf()
//		 .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//	}

//		@Override
//		protected void configure(HttpSecurity http) throws Exception 
//		{
//			http
//			.authorizeRequests()
//			.antMatchers("/demo").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin()
//			.loginPage("/login")
//			.permitAll()
//			.and()
//			.logout()
//			.permitAll();
//		}
}