package com.fundoonotes.utility;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService 
{
	private static final Logger LOGGER=Logger.getLogger(JwtTokenService.class.getName());
	
	private static final String KEY = "nothing";
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	
	public String getJwtToken(String ownerId)
	{
		String token=Jwts.builder()
				.setSubject(ownerId)
				.claim("roles", "user")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+36000000))
				.signWith(SIGNATURE_ALGORITHM, KEY)
				.compact();
		LOGGER.info("Token has been generated with OwnerID : "+ownerId);
		LOGGER.info("Token : "+token);
		return token;
	}
	
	public String verifyToken(String token)
	{
		String ownerId=Jwts.parser()
				.setSigningKey(KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		LOGGER.info("Given Token : "+token+" has been verified");
		LOGGER.info("Token returns OwnerID : "+ownerId);
		return ownerId;
	}
}