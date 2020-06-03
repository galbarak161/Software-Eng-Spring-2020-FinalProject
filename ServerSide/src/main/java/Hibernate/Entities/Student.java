package Hibernate.Entities;

import javax.persistence.*;

@Entity
public class Student extends User {

	public Student() {
	}

	public Student(String userName, String password, String firstName, String lastName, String identityNumber, String emailAddress) {
		super(userName, password, firstName, lastName, identityNumber, emailAddress);
	}

}