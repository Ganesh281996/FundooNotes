package com.fundoonotes.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.Indexed;

public class EmailDTO 
{
	@Email(message = "Enter valid Email")
	@NotEmpty(message="Email must not be Empty")
	@Indexed(unique=true)
	private String email;

	@Override
	public String toString() 
	{
		return "EmailDTO [email=" + email + "]";
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}