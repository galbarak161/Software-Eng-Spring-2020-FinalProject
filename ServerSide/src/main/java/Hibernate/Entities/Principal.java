package Hibernate.Entities;

import javax.persistence.Entity;

@Entity
public class Principal extends User {

	public Principal() {

	}

	public Principal(int id,String userName, String password, String firstName, String lastName, String emailAddress) {
		super(id ,userName, password, firstName, lastName, emailAddress);
	}
}
