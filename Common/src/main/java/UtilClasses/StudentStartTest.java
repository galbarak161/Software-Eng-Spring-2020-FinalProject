package UtilClasses;

import java.io.Serializable;

import CloneEntities.CloneUser;

public class StudentStartTest implements Serializable {

	private static final long serialVersionUID = -6867510437141467706L;

	int userId;
	String executionCode;

	public StudentStartTest(int userId, String executionCode) {
		this.userId = userId;
		this.executionCode = executionCode;
	}

	public int getUserId() {
		return userId;
	}

	public String getEexecutionCode() {
		return executionCode;
	}
}
