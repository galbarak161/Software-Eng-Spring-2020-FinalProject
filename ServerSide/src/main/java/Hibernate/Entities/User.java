package Hibernate.Entities;

import javax.persistence.*;

import CloneEntities.CloneUser;
import CloneEntities.CloneUser.UserType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "User")
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private int id;

	@Column(name = "userName")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "identityNumber")
	private String identityNumber;

	@Column(name = "emailAddress")
	private String emailAddress;
	
	@Column(name = "isLoggedIn")
	private Boolean isLoggedIn;
	
	@Column(name = "clientNumber")
	private int clientNumber;
	
	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, String emailAddress) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		//this.identityNumber = identityNumber;
		isLoggedIn = false;
	}

	public CloneUser createClone() {
		UserType userType = null;

		if (this instanceof Teacher)
			userType = UserType.Teacher;
		else if (this instanceof Student)
			userType = UserType.Student;
		else if (this instanceof Principal)
			userType = UserType.Principal;

		CloneUser clone = new CloneUser(this.id, this.firstName, this.lastName, this.emailAddress, userType);
		clone.setId(id);
		return clone;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	
	
}
