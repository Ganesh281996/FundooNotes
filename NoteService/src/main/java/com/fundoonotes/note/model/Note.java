package com.fundoonotes.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Notes")
public class Note 
{
	@Id
	private String noteId;
	
	private String title;
	
	private String body;
	
	private String createdDate;
	
	private String lastUpdatedDate;
	
	private boolean isPinned;
	
	private boolean isArchieved;
	
	private boolean inTrash;
	
	@DBRef
	private User user;

	@Override
	public String toString() 
	{
		return "Note [noteId=" + noteId + ", title=" + title + ", body=" + body + ", createdDate=" + createdDate
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", isPinned=" + isPinned + ", isArchieved=" + isArchieved
				+ ", inTrash=" + inTrash + ", user=" + user + "]";
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isArchieved() {
		return isArchieved;
	}

	public void setArchieved(boolean isArchieved) {
		this.isArchieved = isArchieved;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}