package Hibernate.Entities;

import javax.persistence.*;

@Entity
public class Student extends User {

	public Student() {
	}

	public Student(int id,String userName, String password, String firstName, String lastName, String emailAddress) {
		super(id,userName, password, firstName, lastName, emailAddress);
	}

}