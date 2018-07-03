package com.fundoonotes.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Notes")
public class Note 
{
	@Id
	private String _id;
	
	private String title;
	
	private String body;
	
	@DBRef
	private User user;

	@Override
	public String toString() 
	{
		return "Note [_id=" + _id + ", title=" + title + ", body=" + body + ", user=" + user + "]";
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}