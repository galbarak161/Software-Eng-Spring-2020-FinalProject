package Hibernate.Entities;

import javax.persistence.*;

@Entity
public class Student extends User{

	public Student() {
	}
	public Student(String userName, String password, String firstName, String lastName) {
		super(userName, password, firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
}