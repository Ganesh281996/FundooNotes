package com.fundoonotes.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DummyUser")
public class User 
{
	@Id
	private String _id;
	
	private String name;

	@Override
	public String toString() 
	{
		return "User [_id=" + _id + ", name=" + name + "]";
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
}