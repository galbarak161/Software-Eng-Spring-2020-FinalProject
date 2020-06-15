package CloneEntities;

import java.io.Serializable;

public class CloneTimeExtensionRequest implements Serializable{
	
	public enum RequestStatus {
		Denied ,Confirmed, Onging;
	}
	private static final long serialVersionUID = 6598660146086038505L;

	private int id;
	
	private String body;
	
	private boolean isRequestConfirmed;
	
	private int timeToExtenedInMinute;
	
	private CloneTest test;
	
	private RequestStatus status;

	public CloneTimeExtensionRequest(int id, String body, int timeToExtenedInMinute, CloneTest test , RequestStatus status) {
		super();
		this.id = id;
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
		this.test = test;
		this.isRequestConfirmed = false;
		this.status = status;
	}

	public CloneTimeExtensionRequest(String body, int timeToExtenedInMinute, CloneTest test) {
		super();
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
		this.test = test;
		this.isRequestConfirmed = false;
		this.status = RequestStatus.Onging;
	}

	public boolean isRequestConfirmed() {
		return isRequestConfirmed;
	}
	


	public void setRequestConfirmed(boolean isRequestConfirmed) {
		if(isRequestConfirmed) {
			this.status = RequestStatus.Confirmed;
		}else {
			this.status = RequestStatus.Denied;
		}
		this.isRequestConfirmed = isRequestConfirmed;
	}

	public int getTimeToExtenedInMinute() {
		return timeToExtenedInMinute;
	}

	public void setTimeToExtenedInMinute(int timeToExtenedInMinute) {
		this.timeToExtenedInMinute = timeToExtenedInMinute;
	}

	public int getId() {
		return id;
	}

	public String getBody() {
		return body;
	}

	public CloneTest getTest() {
		return test;
	}
	
	public String getTestName() {
		return test.getExamToExecute().getExamName();
	}
	
	public String getTestDate() {
		return test.getTestDateInFormat();
	}
	
	public String getTestTime() {
		return String.valueOf(test.getTestTime());
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
	public RequestStatus getStatus() {
		return status;
	}
	
}
