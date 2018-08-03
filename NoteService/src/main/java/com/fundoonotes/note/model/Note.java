package com.fundoonotes.note.model;

import java.util.Date;
import java.util.Set;

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
	
	private Set<Label> labels;
	
	private Set<Collaborator> collaborators;
	
	private Set<WebScrap> webScraps;

	@Override
	public String toString() 
	{
		return "Note [noteId=" + noteId + ", title=" + title + ", body=" + body + ", colour=" + colour + ", remainder="
				+ remainder + ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + ", isPinned="
				+ isPinned + ", isArchieved=" + isArchieved + ", inTrash=" + inTrash + ", ownerId=" + ownerId
				+ ", labels=" + labels + ", collaborators=" + collaborators + ", webScraps=" + webScraps + "]";
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

	public Set<Label> getLabels() {
		return labels;
	}

	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}

	public Set<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(Set<Collaborator> collaborators) {
		this.collaborators = collaborators;
	}

	public Set<WebScrap> getWebScraps() {
		return webScraps;
	}

	public void setWebScraps(Set<WebScrap> webScraps) {
		this.webScraps = webScraps;
	}
}