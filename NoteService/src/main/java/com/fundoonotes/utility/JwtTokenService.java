package com.fundoonotes.utility;

import org.apache.log4j.Logger;

import io.jsonwebtoken.Jwts;

public class JwtTokenService 
{
	private static final Logger LOGGER = Logger.getLogger(JwtTokenService.class);
	
	public String verifyToken(String token)
	{
		String userId = Jwts.parser().
				setSigningKey("secretkey").
				parseClaimsJws(token).
				getBody().
				getSubject();
		LOGGER.info("Given Token : "+token+" has been verified");
		LOGGER.info("Token returns User ID : "+userId);
		return userId;
	}
}