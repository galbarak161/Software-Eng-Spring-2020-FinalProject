package CloneEntities;


public class CloneTimeExtensionRequest {
	
	private int id;
	
	private String body;
	
	private boolean isRequestConfirmed;
	
	private int timeToExtenedInMinute;
	
	private CloneTest test;

	public CloneTimeExtensionRequest(int id, String body, int timeToExtenedInMinute, CloneTest test) {
		super();
		this.id = id;
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
		this.test = test;
		this.isRequestConfirmed = false;
	}

	public CloneTimeExtensionRequest(String body, int timeToExtenedInMinute, CloneTest test) {
		super();
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
		this.test = test;
		this.isRequestConfirmed = false;
	}

	public boolean isRequestConfirmed() {
		return isRequestConfirmed;
	}
	
	public String getStatus() {
		if(this.isRequestConfirmed) 
			return "Confirmed";
		return "Denied";
	}

	public void setRequestConfirmed(boolean isRequestConfirmed) {
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
		return String.valueOf(test.getTestDate());
	}
	
	public String getTestTime() {
		return String.valueOf(test.getTestTime());
	}
	
}
