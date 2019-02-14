package com.metacube.tms.modal;

public class CurrentUser {
	private String email;
	private Long userID;
	private String userUUID;
	private Long organizationID;
	private String organizationUUID;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public Long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Long organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganizationUUID() {
		return organizationUUID;
	}

	public void setOrganizationUUID(String organizationUUID) {
		this.organizationUUID = organizationUUID;
	}

  @Override
  public String toString() {
    return "CurrentUser [email=" + email + ", userID=" + userID + ", userUUID=" + userUUID + ", organizationID="
        + organizationID + ", organizationUUID=" + organizationUUID + "]";
  }

	
	
}
