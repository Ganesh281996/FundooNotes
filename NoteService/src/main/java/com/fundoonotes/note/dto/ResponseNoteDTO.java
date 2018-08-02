package com.fundoonotes.note.dto;

import java.util.Date;
import java.util.List;

import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.WebScrap;

public class ResponseNoteDTO
{
	private String noteId;

	private String title;

	private String body;

	private String colour = "white";

	private Date remainder;

	private String lastUpdatedDate;

	private boolean isPinned = false;

	private boolean isArchieved = false;

	private boolean inTrash = false;

	private String ownerId;

	private List<Label> labels;

	private List<CollaboratorResponseDTO> collaborators;

	private List<WebScrap> webScraps;

	@Override
	public String toString() 
	{
		return "ResponseNoteDTO [noteId=" + noteId + ", title=" + title + ", body=" + body + ", colour=" + colour
				+ ", remainder=" + remainder + ", lastUpdatedDate=" + lastUpdatedDate + ", isPinned=" + isPinned
				+ ", isArchieved=" + isArchieved + ", inTrash=" + inTrash + ", ownerId=" + ownerId + ", labels="
				+ labels + ", collaborators=" + collaborators + ", webScraps=" + webScraps + "]";
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

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public List<CollaboratorResponseDTO> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<CollaboratorResponseDTO> collaborators) {
		this.collaborators = collaborators;
	}

	public List<WebScrap> getWebScraps() {
		return webScraps;
	}

	public void setWebScraps(List<WebScrap> webScraps) {
		this.webScraps = webScraps;
	}
}