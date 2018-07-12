package com.fundoonotes.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Collaborator")
public class Collaborator 
{
	@Id
	private String collaboratorId;
	
	private String sharedBy;
	
	private String sharedTo;
	
	private String noteId;

	@Override
	public String toString() 
	{
		return "Collaborator [collaboratorId=" + collaboratorId + ", sharedBy=" + sharedBy + ", sharedTo=" + sharedTo
				+ ", noteId=" + noteId + "]";
	}

	public String getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(String collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public String getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(String sharedBy) {
		this.sharedBy = sharedBy;
	}

	public String getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
}