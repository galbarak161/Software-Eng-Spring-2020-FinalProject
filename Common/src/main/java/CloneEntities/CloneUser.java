package CloneEntities;

import java.io.Serializable;

public class CloneUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713504832702720811L;

	public enum UserType {
		Student, Teacher, Principal;
	}
	
	private String firstName;
	
	private String lastName;
	
	private String identityNumber;
	
	private String emailAddress;
	
	private UserType userType;

	public CloneUser(String firstName, String lastName, String identityNumber, String emailAddress, UserType userType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
		this.emailAddress = emailAddress;
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}	
	
	public UserType getUserType() {
		return userType;
	}
}
