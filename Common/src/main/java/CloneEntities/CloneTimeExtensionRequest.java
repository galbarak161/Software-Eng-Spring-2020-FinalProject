package CloneEntities;


public class CloneTimeExtensionRequest {
	
	private int id;
	
	private String body;
	
	
	private boolean isRequestConfirmed;
	
	
	private int timeToExtenedInMinute;
	
	
	private CloneTest test;

	public CloneTimeExtensionRequest(String body, int timeToExtenedInMinute) {
		super();
		this.isRequestConfirmed = false;
		this.body = body;
		this.timeToExtenedInMinute = timeToExtenedInMinute;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public CloneTest getTest() {
		return test;
	}

	public void setTest(CloneTest test) {
		this.test = test;
	}

	public boolean isRequestConfirmed() {
		return isRequestConfirmed;
	}
}
