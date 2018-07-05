package com.fundoonotes.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Labels")
public class Label 
{
	@Id
	private String labelId;
	
	@Indexed(unique = true)
	private String labelName;
	
	@DBRef
	private User user;

	@Override
	public String toString() 
	{
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", user=" + user + "]";
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}