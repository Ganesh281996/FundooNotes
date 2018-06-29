package com.fundoonotes.user.service;

import com.fundoonotes.user.model.User;
import com.fundoonotes.utility.Response;

public interface UserService 
{
	public Response register(User user);
	public Response activateUser(String token);
	public Response login(String email,String password);
	public Response forgotPassword(String email);
	public Response verifyToken(String token);
	public Response resetPassword(String token,String password1,String password2);
}