package Hibernate.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
// https://www.oracle.com/technical-resources/articles/java/jf14-date-time.html
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "Test")
public class Test extends Exam{
	public enum ExamType {
		Automated,Manual;
	}
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "testId")
	private int id;
	
	
	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "teacherId")
	private Teacher executor;
	
	//@Column(name = "testDate")
	private LocalDate testDate;
	
	//@Column(name = "testTime")
	private LocalTime testTime;
    
	//@Column(name = "executionCode")
    private int executionCode;

	//@Column(name = "students")
	private List<Student> students; 
	
	//@Column(name = "extraMinutes")
    private int extraMinute = 0;
	
	//@Column(name = "type")
	private ExamType type;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "test")
	private List<TimeExtensionRequest> requests;
	

	public Test(Teacher creator, List<Integer> questionsPoints, int duration, Study study,
			Course course, String teacherComments, String studentComments) {
		super(creator, questionsPoints, duration, study, course, teacherComments, studentComments);
		// TODO Auto-generated constructor stub
	}
	


	public Test(Teacher executor, LocalDate testDate, LocalTime testTime, int executionCode,
			List<Student> students, int extraMinutes, ExamType type, List<TimeExtensionRequest> requests) {
		super();
		this.executor = executor;
		this.testDate = testDate;
		this.testTime = testTime;
		this.executionCode = executionCode;
		this.students = students;
		this.extraMinute = extraMinutes;
		this.type = type;
		this.requests = requests;
	}



	public Teacher getExecutor() {
		return executor;
	}


	public void setExecutor(Teacher executor) {
		this.executor = executor;
	}


	public LocalDate getTestDate() {
		return testDate;
	}


	public void setTestDate(LocalDate testDate) {
		this.testDate = testDate;
	}


	public LocalTime getTestTime() {
		return testTime;
	}


	public void setTestTime(LocalTime testTime) {
		this.testTime = testTime;
	}


	public int getExecutionCode() {
		return executionCode;
	}


	public void setExecutionCode(int executionCode) {
		this.executionCode = executionCode;
	}


	public List<Student> getStudents() {
		return students;
	}


	public void setStudents(List<Student> students) {
		this.students = students;
	}


	public int getExtraMinutes() {
		return extraMinute;
	}


	public void setExtraMinutes(int extraMinutes) {
		this.extraMinute = extraMinutes;
	}


	public ExamType getType() {
		return type;
	}


	public void setType(ExamType type) {
		this.type = type;
	}


	public int getId() {
		return id;
	}



	public List<TimeExtensionRequest> getRequests() {
		return requests;
	}



	public void setRequests(List<TimeExtensionRequest> requests) {
		this.requests = requests;
	}
	
	
}
