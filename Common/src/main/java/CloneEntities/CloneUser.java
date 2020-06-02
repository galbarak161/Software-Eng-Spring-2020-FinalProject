package CloneEntities;


public class CloneUser {
	
	public enum UserType {
		Student, Teacher, Principal;
	}
	
	private String firstName;
	
	private String lastName;
	
	private String identityNumber;
	
	private UserType userType;

	public CloneUser(String firstName, String lastName, String identityNumber, UserType userType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
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

	public UserType getUserType() {
		return userType;
	}	
}
