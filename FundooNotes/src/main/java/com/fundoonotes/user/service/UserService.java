package com.fundoonotes.user.service;

import com.fundoonotes.user.dto.RegisterUserDTO;

public interface UserService 
{
	public void register(RegisterUserDTO registerUserDTO);
	public void activateUser(String token);
	public String login(String email,String password);
	public void forgotPassword(String email);
	public void verifyToken(String token);
	public void resetPassword(String token,String password1,String password2);
}