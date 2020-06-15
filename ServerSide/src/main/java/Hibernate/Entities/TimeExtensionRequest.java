package Hibernate.Entities;

import javax.persistence.*;

import CloneEntities.CloneTimeExtensionRequest;
import CloneEntities.CloneTimeExtensionRequest.RequestStatus;

@Entity
@Table(name = "Time_Extension_Request")
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
	
	@Column(name = "status")
	private RequestStatus status;

	@OneToOne(mappedBy = "extensionRequests", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Test test;

	public TimeExtensionRequest() {
	}

	public TimeExtensionRequest(String body, int timeToExtenedInMinute) {
		this.isRequestConfirmed = false;
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
		this.status = RequestStatus.Onging;
	}

	public CloneTimeExtensionRequest createClone() {
		CloneTimeExtensionRequest clone = new CloneTimeExtensionRequest(id, body, timeToExtenedInMinute,
				getTest().createClone() , status);
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

	
	
	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public void setRequestConfirmed(boolean isRequestConfirmed) {
		if(isRequestConfirmed) {
			setStatus(RequestStatus.Confirmed); 
		}else {
			setStatus(RequestStatus.Denied); 
		}
		this.isRequestConfirmed = isRequestConfirmed;
		if (this.isRequestConfirmed == true) {
			this.getTest().setExtensionRequests(this);
		}
	}

}
