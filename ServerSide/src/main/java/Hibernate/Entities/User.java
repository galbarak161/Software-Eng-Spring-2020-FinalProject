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

	@Column(name = "emailAddress")
	private String emailAddress;

	@Column(name = "isLoggedIn")
	private boolean isLoggedIn;

	@Column(name = "isPrincipal")
	private boolean isPrincipal;
	
	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, String emailAddress, boolean isPrincipal) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		isLoggedIn = false;
		this.isPrincipal = isPrincipal;
	}

	public CloneUser createClone() {
		UserType userType = null;

		if (this instanceof Teacher)
			userType = UserType.Teacher;
		else if (this instanceof Student)
			userType = UserType.Student;
		else if (this instanceof Principal)
			userType = UserType.Principal;

		CloneUser clone = new CloneUser(id, firstName, lastName, emailAddress, userType);
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public boolean isPrincipal() {
		return isPrincipal;
	}
}
