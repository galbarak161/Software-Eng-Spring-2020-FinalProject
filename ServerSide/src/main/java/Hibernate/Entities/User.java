package Hibernate.Entities;

import javax.persistence.*;

import CloneEntities.CloneStudy;
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

	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, String identityNumber) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
	}

	public CloneUser createClone() {
		UserType userType = null;

		if (this instanceof Teacher)
			userType = UserType.Teacher;
		else if (this instanceof Student)
			userType = UserType.Student;
		else if (this instanceof Principal)
			userType = UserType.Principal;

		CloneUser clone = new CloneUser(this.firstName, this.lastName, this.identityNumber, userType);
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

}
