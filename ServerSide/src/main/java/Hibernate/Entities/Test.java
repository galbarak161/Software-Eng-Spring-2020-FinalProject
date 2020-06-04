package Hibernate.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Test")
public class Test {

	public enum ExamType {
		Automated, Manual;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "testId")
	private int id;

	@Column(name = "testDate")
	private LocalDate testDate;

	@Column(name = "testTime")
	private LocalTime testTime;

	@Column(name = "executionCode")
	private String executionCode;

	@Column(name = "extraMinutes")
	private int extraMinute = 0;
	
	@Column(name = "testDuration")
	private int testDuration;

	@Column(name = "examType")
	private ExamType type;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "timeExtensionRequestId")
	private TimeExtensionRequest extensionRequests;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "teacherId")
	private Teacher executor;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "test")
	private List<StudentTest> students;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "examId")
	private Exam examToExecute;

	public Test() {
		super();
		students = new ArrayList<StudentTest>();
	}

	public Test(LocalDate testDate, LocalTime testTime, String executionCode, ExamType type, Teacher executor,
			Exam examToExecute) {
		super();
		this.testDate = testDate;
		this.testTime = testTime;
		this.executionCode = executionCode;
		this.extraMinute = 0;
		this.type = type;
		setExtensionRequests(null);
		setExecutor(executor);
		setExamToExecute(examToExecute);
		students = new ArrayList<StudentTest>();
	}

	public int getId() {
		return id;
	}

	public String getExecutionCode() {
		return executionCode;
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

	public int getExtraMinute() {
		return extraMinute;
	}

	public void setExtraMinute(int extraMinute) {
		this.extraMinute = extraMinute;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public TimeExtensionRequest getExtensionRequests() {
		return extensionRequests;
	}

	public void setExtensionRequests(TimeExtensionRequest extensionRequests) {
		if (extensionRequests != null) {
			this.extensionRequests = extensionRequests;
			extensionRequests.setTest(this);
			this.extraMinute += extensionRequests.getTimeToExtenedInMinute();
		}
	}

	public Teacher getExecutor() {
		return executor;
	}

	public void setExecutor(Teacher executor) {
		this.executor = executor;
		executor.getTests().add(this);
	}

	public List<StudentTest> getStudents() {
		return students;
	}

	public void addStudent(StudentTest student) {
		this.students.add(student);
	}

	public Exam getExamToExecute() {
		return examToExecute;
	}

	public void setExamToExecute(Exam examToExecute) {
		this.examToExecute = examToExecute;
		examToExecute.getTests().add(this);
		testDuration = examToExecute.getDuration();
	}

}
