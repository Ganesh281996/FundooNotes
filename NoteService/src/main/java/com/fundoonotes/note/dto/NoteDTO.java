package com.fundoonotes.note.dto;

import java.util.Date;
import java.util.List;

public class NoteDTO 
{
	private String title;
	
	private String body;
	
	private String colour = "white";
	
	private Date remainder;
	
	private boolean isPinned = false;
	
	private boolean isArchieved = false;
	
	private List<String> collaborators;

	private List<String> labels;

	@Override
	public String toString() 
	{
		return "CreateNoteDTO [title=" + title + ", body=" + body + ", colour=" + colour + ", remainder=" + remainder
				+ ", isPinned=" + isPinned + ", isArchieved=" + isArchieved + ", collaborators=" + collaborators
				+ ", labels=" + labels + "]";
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

	public List<String> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<String> collaborators) {
		this.collaborators = collaborators;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
}