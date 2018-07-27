package com.fundoonotes.note.model;

public class WebScrap 
{
	private String title;
	
	private String url;
	
	private String image;

	@Override
	public String toString() 
	{
		return "WebScrap [title=" + title + ", url=" + url + ", image=" + image + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}