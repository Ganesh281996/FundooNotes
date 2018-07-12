package com.fundoonotes.note.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Notes")
public class Note 
{
	@Id
	private String noteId;
	
	private String title;
	
	private String body;
	
	private String colour = "white";
	
	private Date remainder;
	
	private String createdDate;
	
	private String lastUpdatedDate;
	
	private boolean isPinned = false;
	
	private boolean isArchieved = false;
	
	private boolean inTrash = false;
	
	private String ownerId;
	
	private List<String> labels;
	
	private List<String> collaborators;

	@Override
	public String toString() 
	{
		return "Note [noteId=" + noteId + ", title=" + title + ", body=" + body + ", colour=" + colour + ", remainder="
				+ remainder + ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + ", isPinned="
				+ isPinned + ", isArchieved=" + isArchieved + ", inTrash=" + inTrash + ", ownerId=" + ownerId
				+ ", labels=" + labels + ", collaborators=" + collaborators + "]";
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

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Date getRemainder() {
		return remainder;
	}

	public void setRemainder(Date remainder) {
		this.remainder = remainder;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<String> collaborators) {
		this.collaborators = collaborators;
	}
}