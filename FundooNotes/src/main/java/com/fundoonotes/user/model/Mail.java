package com.fundoonotes.user.model;

import java.io.Serializable;

public class Mail implements Serializable
{
	private static final long serialVersionUID = -2250547771055372106L;

	private String from;
	
	private String to;
	
	private String subject;
	
	private String text;

	@Override
	public String toString() 
	{
		return "Mail [from=" + from + ", to=" + to + ", subject=" + subject + ", text=" + text + "]";
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}