package com.fundoonotes.note.utility;

import org.springframework.http.HttpStatus;

public class Response
{
	private String message;
	private Object data;
	private HttpStatus httpStatus;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	@Override
	public String toString() 
	{
		return "Response [message=" + message + ", data=" + data + ", httpStatus=" + httpStatus + "]";
	}
}