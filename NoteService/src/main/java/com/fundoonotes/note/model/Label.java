package com.fundoonotes.note.model;

import java.util.List;

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
	
	@DBRef(lazy = true)
	private List<Note> notes;

	@Override
	public String toString()
	{
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", user=" + user + ", notes=" + notes + "]";
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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
}