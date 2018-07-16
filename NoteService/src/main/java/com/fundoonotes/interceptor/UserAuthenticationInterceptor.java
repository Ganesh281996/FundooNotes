package com.fundoonotes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fundoonotes.utility.JwtTokenService;

public class UserAuthenticationInterceptor implements HandlerInterceptor
{
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception 
	{
		String ownerId = jwtTokenService.verifyToken(request.getHeader("token"));
		request.setAttribute("ownerId", ownerId);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception 
	{
		request.removeAttribute("ownerId");
	}
}
