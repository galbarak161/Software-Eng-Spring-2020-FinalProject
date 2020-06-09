package UtilClasses;

import java.io.Serializable;

import CloneEntities.CloneUser;
import java.lang.String;

public class StudentExamCode implements Serializable {

	private static final long serialVersionUID = -6867510437141467706L;
	
	CloneUser user;
	String examCode;
	
	public StudentExamCode(CloneUser user, String examCode) {
		this.user = user;
		this.examCode = examCode;
	}

	public CloneUser getUser() {
		return user;
	}

	public String getExamCode() {
		return examCode;
	}
	
	
}

