package com.fundoonotes.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterUserDTO 
{
	@NotEmpty(message = "Name must not be Empty")
	@Size(min = 4, max = 20, message = "Name must contain 4-20 characters")
	private String name;

	@Email(message = "Enter valid Email")
	@NotEmpty(message="Email must not be Empty")
	private String email;

	@NotBlank(message = "Password must not be blank")
	@Size(min=4,max=100,message="Enter Strong Password")
	private String password;

	@Min(999999999) @Max(10000000000L)
	private long phoneNumber;

	@Override
	public String toString() 
	{
		return "RegisterUserDTO [name=" + name + ", email=" + email + ", password=" + password + ", phoneNumber="
				+ phoneNumber + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}