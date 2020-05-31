package Hibernate.Entities;

import javax.persistence.Entity;

@Entity
public class Director extends User {

	public Director() {
		
	}
	public Director(String userName, String password, String firstName, String lastName) {
		super(userName, password, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

}
