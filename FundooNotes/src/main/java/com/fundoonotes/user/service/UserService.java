package com.fundoonotes.user.service;

import com.fundoonotes.user.dto.EmailDTO;
import com.fundoonotes.user.dto.LoginDTO;
import com.fundoonotes.user.dto.RegisterUserDTO;
import com.fundoonotes.user.model.User;

public interface UserService 
{
	public void register(RegisterUserDTO registerUserDTO);
	public void activateUser(String token);
	public String login(LoginDTO loginDTO);
	public void verifyToken(String token);
	public void resetPassword(String token,String password1,String password2);
	void forgotPassword(EmailDTO emailDTO);
	User getUserByEmail(String email);
}