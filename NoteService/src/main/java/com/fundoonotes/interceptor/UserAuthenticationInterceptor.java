package com.fundoonotes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fundoonotes.utility.JwtTokenService;

public class UserAuthenticationInterceptor implements HandlerInterceptor
{
	@Autowired
	JwtTokenService jwtTokenService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception 
	{
		LOGGER.info("Verifying Token");
		String ownerId = jwtTokenService.verifyToken(request.getHeader("token"));
		request.setAttribute("ownerId", ownerId);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception 
	{
		LOGGER.info("Removing OwnerID from request");
		request.removeAttribute("ownerId");
	}
}
