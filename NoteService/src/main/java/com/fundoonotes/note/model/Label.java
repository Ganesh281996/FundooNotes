package com.fundoonotes.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Labels")
public class Label 
{
	@Id
	private String labelId;
	
//	@Indexed(unique = true)
	private String labelName;
	
	private String userId;
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labelId == null) ? 0 : labelId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (labelId == null) {
			if (other.labelId != null)
				return false;
		} else if (!labelId.equals(other.labelId))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", userId=" + userId + "]";
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}