package com.fundoonotes.user.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Users")
public class User implements Serializable
{
	private static final long serialVersionUID = 7267769044647652333L;

	@Id
	private String _id;

	@Field
	@NotEmpty(message = "Name must not be Empty")
	@Size(min = 4, max = 20, message = "Name must contain 4-20 characters")
	private String name;

	@Field
	@Email(message = "Enter valid Email")
	@NotEmpty(message="Email must not be Empty")
	@Indexed(unique=true)
	private String email;

	@Field
	@NotBlank(message = "Password must not be blank")
	@Size(min=4,max=100,message="Enter Strong Password")
	private String password;

	@Field
	@Min(999999999) @Max(10000000000L)
	@Indexed(unique=true)
	private long phoneNumber;

	@Field
	private boolean isVerified;
	
	@Override
	public String toString() 
	{
		return "User [_id=" + _id + ", name=" + name + ", email=" + email + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", isVerified=" + isVerified + "]";
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
}