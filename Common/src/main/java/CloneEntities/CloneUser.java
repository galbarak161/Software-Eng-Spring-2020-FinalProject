package CloneEntities;

import java.io.Serializable;

public class CloneUser implements Serializable {

	private static final long serialVersionUID = -3713504832702720811L;

	public enum UserType {
		Student, Teacher, Principal;
	}

	private int id;
	
	private String identifyNumber;

	private String firstName;

	private String lastName;

	private String emailAddress;

	private UserType userType;

	public CloneUser(int id, String identifyNumber, String firstName, String lastName, String emailAddress, UserType userType) {
		this.identifyNumber = identifyNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.emailAddress = emailAddress;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public UserType getUserType() {
		return userType;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}
}
