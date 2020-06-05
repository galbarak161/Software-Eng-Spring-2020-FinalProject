package Hibernate.Entities;

import javax.persistence.*;

import CloneEntities.CloneTest;
import CloneEntities.CloneTimeExtensionRequest;

@Entity
@Table(name = "TimeExtensionRequest")
public class TimeExtensionRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timeExtensionRequestId")
	private int id;

	@Column(name = "bodyRequest")
	private String body;

	@Column(name = "isRequestConfirmed")
	private boolean isRequestConfirmed;

	@Column(name = "timeToExtenedInMinute")
	private int timeToExtenedInMinute;

	@OneToOne(mappedBy = "extensionRequests", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Test test;

	public TimeExtensionRequest(String body, int timeToExtenedInMinute) {
		super();
		this.isRequestConfirmed = false;
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
	}

	public CloneTimeExtensionRequest createClone() {

		CloneTimeExtensionRequest clone = new CloneTimeExtensionRequest(getBody(), getTimeToExtenedInMinute());
		clone.setTest(getTest().createClone());
		return clone;

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

	public boolean isRequestConfirmed() {
		return isRequestConfirmed;
	}

	public void setRequestConfirmed(boolean isRequestConfirmed) {
		this.isRequestConfirmed = isRequestConfirmed;
		if (this.isRequestConfirmed == true) {
			this.getTest().setExtensionRequests(this);
		}
	}

}
