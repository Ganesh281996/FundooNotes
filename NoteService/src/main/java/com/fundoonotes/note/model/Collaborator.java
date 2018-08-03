package com.fundoonotes.note.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Collaborator")
public class Collaborator 
{
	private String collaboratorEmail;

	private String collaboratorName;

	private String collaboratorProfilePic;

	@Override
	public String toString() 
	{
		return "Collaborator [collaboratorEmail=" + collaboratorEmail + ", collaboratorName=" + collaboratorName
				+ ", collaboratorProfilePic=" + collaboratorProfilePic + "]";
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collaboratorEmail == null) ? 0 : collaboratorEmail.hashCode());
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
		Collaborator other = (Collaborator) obj;
		if (collaboratorEmail == null) {
			if (other.collaboratorEmail != null)
				return false;
		} else if (!collaboratorEmail.equals(other.collaboratorEmail))
			return false;
		return true;
	}

	public String getCollaboratorEmail() {
		return collaboratorEmail;
	}

	public void setCollaboratorEmail(String collaboratorEmail) {
		this.collaboratorEmail = collaboratorEmail;
	}

	public String getCollaboratorName() {
		return collaboratorName;
	}

	public void setCollaboratorName(String collaboratorName) {
		this.collaboratorName = collaboratorName;
	}

	public String getCollaboratorProfilePic() {
		return collaboratorProfilePic;
	}

	public void setCollaboratorProfilePic(String collaboratorProfilePic) {
		this.collaboratorProfilePic = collaboratorProfilePic;
	}
}