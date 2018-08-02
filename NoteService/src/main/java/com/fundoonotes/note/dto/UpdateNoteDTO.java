package com.fundoonotes.note.dto;

public class UpdateNoteDTO 
{
	private String noteId;

	private String title;

	private String body;

	@Override
	public String toString() 
	{
		return "UpdateNoteDTO [noteId=" + noteId + ", title=" + title + ", body=" + body + "]";
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
}