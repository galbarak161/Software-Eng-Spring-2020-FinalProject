package Hibernate.Entities;

import javax.persistence.*;

@Entity
@Table(name = "TimeExtensionRequest")
public class TimeExtensionRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timeExtensionRequestId")
	private int id;
	
	@Column(name = "bodyRequest")
	private String body;
	
	@Column(name = "timeToExtenedInMinute")
	private int timeToExtenedInMinute;
	
	@OneToOne(mappedBy = "extensionRequests", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Test test;

	public TimeExtensionRequest(String body, int timeToExtenedInMinute) {
		super();
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
	}

	public int getId() {
		return id;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getTimeToExtenedInMinute() {
		return timeToExtenedInMinute;
	}

	public void setTimeToExtenedInMinute(int timeToExtenedInMinute) {
		this.timeToExtenedInMinute = timeToExtenedInMinute;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
}
