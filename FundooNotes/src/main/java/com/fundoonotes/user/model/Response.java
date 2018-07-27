package com.fundoonotes.user.model;

public class Response
{
	private String message;
	
	public Response(String message) 
	{
		this.message = message;
	}
	
	public Response() 
	{
		
	}

	@Override
	public String toString() 
	{
		return "Response [message=" + message + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}