package Hibernate.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Principal extends User {

	public Principal() {
		super();
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "principle")
	private List<TimeExtensionRequest> requests;
	
	public Principal(String userName, String password, String firstName, String lastName, String emailAddress) {
		super(userName, password, firstName, lastName, emailAddress);
		
		
		
	}

	public List<TimeExtensionRequest> getRequests() {
		return requests;
	}

	public void addRequests(TimeExtensionRequest request) {
		requests.add(request);
	}
	
	
	
}
