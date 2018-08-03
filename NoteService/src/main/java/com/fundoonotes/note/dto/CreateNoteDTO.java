package com.fundoonotes.note.dto;

import java.util.Date;
import java.util.Set;

public class CreateNoteDTO 
{
	private String title;

	private String body;

	private String colour = "white";

	private Date remainder;

	private boolean isPinned = false;

	private boolean isArchieved = false;

	private boolean inTrash = false;

	private Set<String> labelNames;

	private Set<String> collaboratorEmailIds;

	@Override
	public String toString() 
	{
		return "CreateNoteDTO [title=" + title + ", body=" + body + ", colour=" + colour + ", remainder=" + remainder
				+ ", isPinned=" + isPinned + ", isArchieved=" + isArchieved + ", inTrash=" + inTrash + ", labelNames="
				+ labelNames + ", collaboratorEmailIds=" + collaboratorEmailIds + "]";
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

	public Set<String> getLabelNames() {
		return labelNames;
	}

	public void setLabelNames(Set<String> labelNames) {
		this.labelNames = labelNames;
	}

	public Set<String> getCollaboratorEmailIds() {
		return collaboratorEmailIds;
	}

	public void setCollaboratorEmailIds(Set<String> collaboratorEmailIds) {
		this.collaboratorEmailIds = collaboratorEmailIds;
	}
}