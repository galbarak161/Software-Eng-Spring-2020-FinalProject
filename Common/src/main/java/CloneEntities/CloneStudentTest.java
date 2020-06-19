package CloneEntities;

import java.io.File;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

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
	
	private File uploadedFile;
	
	public CloneStudentTest(int id, CloneUser student, CloneTest test, 
			LocalTime startTime, int grade, int actualTestDurationInMinutes, 
			String examCheckNotes, StudentTestStatus status) {
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
		if (grade == -1)
			return "0";
		return String.valueOf(grade);
	}

	public String getStringGrade() {
		if (status != StudentTestStatus.Done)
			return "";
		if(grade == -1)
			return "0";
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

	public void setAnswers(List<CloneAnswerToQuestion> _answers) {
		int i = 0;
		this.answers = new CloneAnswerToQuestion[_answers.size()];
		for (CloneAnswerToQuestion q: _answers)
		{
			this.answers[i] = q;
			i++;
		}
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}

	public int getActualTestDurationInMinutes() {
		return actualTestDurationInMinutes;
	}
	
	public String getStudentName() {
		return student.getFirstName() + " " + student.getLastName();
	}
	
	public String getStudentID() {
		return String.valueOf(student.getIdentifyNumber());
	}
	
	public String getStudentEmail() {
		return student.getEmailAddress();
	}
	
	public void setactualTestDurationInMinutes(int val) {
		actualTestDurationInMinutes = val;
	}
	
	@Override
	public String toString() {
		return getTest().getExamToExecute().getExamName();
	}
	
	public String getDone() {
		if(grade == -1)
			return "Not Done";
		return "Done";
	}

	public File getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}