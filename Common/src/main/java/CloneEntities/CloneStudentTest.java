package CloneEntities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class CloneStudentTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1308315666481290739L;
	
	public enum StudentTestStatus {
		Done, Scheduled, WaitingForResulte, Ongoing;
	}

	private int id;
	
	private int grade;

	private Map<Integer, AnsweredQuestion> AnsweredQuestions; // each integer is between 1..4

	private String examCheckNotes;

	private CloneUser student;

	private CloneTest test;
	
	private LocalTime startTime; // the time when the student goes into the test
	
	private LocalTime endTime; // the time when the student goes out of the test
	
	
	private double StudentDuration;

	public Map<Integer, AnsweredQuestion> getAnsweredQuestions() {
		return AnsweredQuestions;
	}

	public void setAnsweredQuestions(Map<Integer, AnsweredQuestion> answeredQuestions) {
		AnsweredQuestions = answeredQuestions;
	}
	
	public String getKey(int i)
	{
		return String.valueOf(AnsweredQuestions.keySet().toArray()[i]);
	}
	
	public String getTestName()
	{
		return test.getExamToExecute().getExamName();
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public double getStudentDuration() {
		return StudentDuration;
	}

	public void setStudentDuration(double studentDuration) {
		StudentDuration = studentDuration;
	}

	public CloneStudentTest() {
		super();
	}

	public CloneStudentTest(CloneUser student, CloneTest test) {
		super();
		this.grade = -1;
		this.examCheckNotes = "";
		setTest(test);
		setStudent(student);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CloneTest getTest() {
		return test;
	}

	public void setTest(CloneTest test) {
		this.test = test;
	}

	public CloneUser getStudent() {
		return student;
	}

	public void setStudent(CloneUser student) {
		this.student = student;
	}
	
	@Override
	public String toString() {
		return getTest().getExamToExecute().getExamName();
	}

	public String getGrade() {
		if(this.grade == -1) {
			return "";
		}
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
	
	public String getDate() {
		return test.getTestDate().toString();
	}
	
	public String getTime() {
		return test.getTestTime().toString();
	}
	
	public String getStatus() {
		return "i";
	}
	
	public String getExecutionCode() {
		return test.getExecutionCode();
	}
}