package CloneEntities;

import java.io.Serializable;
import java.time.LocalTime;

public class CloneStudentTest implements Serializable {

	private static final long serialVersionUID = -1308315666481290739L;

	public enum StudentTestStatus {
		Done, Scheduled, WaitingForResult, Ongoing;
	}

	private int id;

	private int grade;

	private String examCheckNotes;

	private CloneUser student;

	private CloneTest test;

	private StudentTestStatus status;

	private CloneAnswerToQuestion[] answers;

	private LocalTime startTime;

	private int actualTestDurationInMinutes;

	public CloneStudentTest(int id, CloneUser student, CloneTest test, LocalTime startTime, int grade, int actualTestDurationInMinutes, String examCheckNotes, StudentTestStatus status) {
		this.id = id;
		this.student = student;
		this.test = test;
		this.startTime = startTime;
		this.grade = grade;
		this.actualTestDurationInMinutes = actualTestDurationInMinutes;
		this.examCheckNotes = examCheckNotes;
		this.status = status;
		this.answers = new CloneAnswerToQuestion[test.getNumberOfQuestions()];
	}

	public int getId() {
		return id;
	}

	public int getGrade() {
		return grade;
	}
	
	public String getStudentGrade() {
		return String.valueOf(grade);
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

	public String getTestName() {
		return test.getName();
	}

	public String getTestDate() {
		return test.getTestDateInFormat();
	}

	public String getTestTime() {
		return test.getTestTime().toString();
	}

	public CloneAnswerToQuestion[] getAnswers() {
		return answers;
	}

	public void addAnswers(int index, CloneAnswerToQuestion answer) {
		this.answers[index] = answer;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}

	public int getActualTestDurationInMinutes() {
		return actualTestDurationInMinutes;
	}
	
	public String getStudnetName() {
		return student.getFirstName() + " " + student.getLastName();
	}
	
	public String getStudentID() {
		return String.valueOf(student.getId());
	}
	
	public String getStudentEmail() {
		return student.getEmailAddress();
	}
	
	public void setactualTestDurationInMinutes(int val) {
		actualTestDurationInMinutes = val;
	}
	
	public void setAnswers(CloneAnswerToQuestion[] answers) {
		for (int i = 0; i < answers.length; i++) {
			this.answers[i] = answers[i];
		}
	}

	@Override
	public String toString() {
		return getTest().getExamToExecute().getExamName();
	}
}