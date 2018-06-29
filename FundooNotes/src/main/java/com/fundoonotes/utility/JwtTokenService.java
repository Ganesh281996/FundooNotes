package com.fundoonotes.utility;

import java.util.Date;
import java.util.logging.Logger;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenService 
{
	private static final Logger LOGGER=Logger.getLogger(JwtTokenService.class.getName());
	
	private static final String KEY = "secretkey";
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	
	public String getJwtToken(String _id)
	{
		String token=Jwts.builder()
				.setSubject(_id)
				.claim("roles", "user")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+36000))
				.signWith(SIGNATURE_ALGORITHM, KEY)
				.compact();
		LOGGER.info("Token has been generated with Users ID : "+_id);
		LOGGER.info("Token : "+token);
		return token;
	}
	
	public String verifyToken(String token)
	{
		String userId=Jwts.parser()
				.setSigningKey(KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		LOGGER.info("Given Token : "+token+" has been verified");
		LOGGER.info("Token returns User ID : "+userId);
		return userId;
	}
}