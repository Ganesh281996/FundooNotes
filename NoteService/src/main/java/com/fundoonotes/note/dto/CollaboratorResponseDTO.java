package com.fundoonotes.note.dto;

public class CollaboratorResponseDTO 
{
	private String email;
	
	private String name;
	
	private String profilePic;

	@Override
	public String toString() 
	{
		return "CollaboratorResponseDTO [email=" + email + ", name=" + name + ", profilePic=" + profilePic + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
}