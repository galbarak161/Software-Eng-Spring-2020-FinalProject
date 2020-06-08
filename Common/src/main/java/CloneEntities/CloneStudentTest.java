package CloneEntities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import CloneEntities.CloneStudentTest.StudentTestStatus;
import UtilClasses.AnsweredQuestion;

public class CloneStudentTest implements Serializable {

	private static final long serialVersionUID = -1308315666481290739L;
	
	public enum StudentTestStatus {
		Done, Scheduled, WaitingForResulte, Ongoing;
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

	public void setStatus(StudentTestStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return getTest().getExamToExecute().getExamName();
	}
}