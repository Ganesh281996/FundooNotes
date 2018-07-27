package com.fundoonotes.note.dto;

import java.util.Date;
import java.util.List;

public class CreateNoteDTO 
{	
	private String title;
	
	private String body;
	
	private String colour = "white";
	
	private Date remainder;
	
	private boolean isPinned = false;
	
	private boolean isArchieved = false;
	
	private boolean inTrash = false;
	
	private List<String> labels;
	
	private List<String> collaboratorEmailIds;

	@Override
	public String toString() 
	{
		return "CreateNoteDTO [title=" + title + ", body=" + body + ", colour=" + colour + ", remainder=" + remainder
				+ ", isPinned=" + isPinned + ", isArchieved=" + isArchieved + ", inTrash=" + inTrash + ", labels="
				+ labels + ", collaboratorEmailIds=" + collaboratorEmailIds + "]";
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

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getCollaboratorEmailIds() {
		return collaboratorEmailIds;
	}

	public void setCollaboratorEmailIds(List<String> collaboratorEmailIds) {
		this.collaboratorEmailIds = collaboratorEmailIds;
	}
}