package CloneEntities;

import java.io.Serializable;

public class CloneStudentTest implements Serializable {

	private static final long serialVersionUID = -1308315666481290739L;
	
	public enum StudentTestStatus {
		Done, Scheduled, WaitingForResult, Ongoing;
	}

	private int id;
	
	private int grade;

	//private Map<Integer, AnsweredQuestion> AnsweredQuestions; // each integer is between 1..4

	private String examCheckNotes;

	private CloneUser student;

	private CloneTest test;
	
	private StudentTestStatus status;

	
	public CloneStudentTest(int id, CloneUser student, CloneTest test) {
		this.id = id;
		this.grade = -1;
		this.examCheckNotes = "";
		this.status = StudentTestStatus.Scheduled;
		this.student = student;
		this.test = test;
	}

	public CloneStudentTest(CloneUser student, CloneTest test) {
		this.grade = -1;
		this.examCheckNotes = "";
		this.status = StudentTestStatus.Scheduled;
		this.student = student;
		this.test = test;
	}

	public int getId() {
		return id;
	}

	public int getGrade() {
		return grade;
	}
	
	public String getStringGrade() {
		if (grade == -1)
			return "";
		return String.valueOf(grade);
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getExamCheckNotes() {
		return examCheckNotes;
	}

	public void setExamCheckNotes(String examCheckNotes) {
		this.examCheckNotes = examCheckNotes;
	}
	
	public CloneUser getStudent() {
		return student;
	}

	public CloneTest getTest() {
		return test;
	}

	public StudentTestStatus getStatus() {
		return status;
	}
	
	public String getStringStatus() {
		return status.toString();
	}

	public void setStatus(StudentTestStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return getTest().getExamToExecute().getExamName();
	}
	
	public String getTestName() {
		return test.getName();
	}
	
	public String getTestDate() {
		return test.getTestDate().toString();
	}
	
	public String getTestTime() {
		return test.getTestTime().toString();
	}
}