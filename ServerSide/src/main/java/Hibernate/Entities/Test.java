package Hibernate.Entities;

import javax.persistence.*;

import CloneEntities.CloneTest;
import CloneEntities.CloneTest.ExamType;
import CloneEntities.CloneTest.TestStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Test")
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "testId")
	private int id;

	@Column(name = "testDate")
	private String testDate;

	@Column(name = "testTime")
	private LocalTime testTime;

	@Column(name = "executionCode")
	private String executionCode;

	@Column(name = "extraMinutes")
	private int extraMinute;

	@Column(name = "testDuration")
	private int testDuration;

	@Column(name = "examType")
	private ExamType type;

	@Column(name = "status")
	private TestStatus status;

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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "testStatisticsId")
	private TestStatistics statistics;

	private int numberOfQuestionInExam;

	public Test() {
		students = new ArrayList<StudentTest>();
		numberOfQuestionInExam = 0;
	}

	public Test(LocalDate testDate, LocalTime testTime, ExamType type, Teacher executor, Exam examToExecute,
			TestStatistics statistics) {
		setTestDate(testDate);
		this.testTime = testTime;
		this.executionCode = TestCodeGenerator();
		this.extraMinute = 0;
		this.type = type;
		this.status = TestStatus.Scheduled;
		setExtensionRequests(null);
		setExecutor(executor);
		setExamToExecute(examToExecute);
		setStatistics(statistics);
		numberOfQuestionInExam = examToExecute.getNumberOfQuestionInExam();
		students = new ArrayList<StudentTest>();
	}

	public CloneTest createClone() {
		String[] values = testDate.split("\\.");
		int day = Integer.parseInt(values[0]);
		int month = Integer.parseInt(values[1]);
		int year = Integer.parseInt(values[2]);
		LocalDate localDate = LocalDate.of(year, month, day);

		CloneTest clone = new CloneTest(id, localDate, testTime, executionCode, extraMinute, testDuration, type, status,
				executor.getId(), examToExecute.createClone(), numberOfQuestionInExam, statistics.createClone());

		return clone;
	}

	public int getId() {
		return id;
	}

	public String getExecutionCode() {
		return executionCode;
	}

	public void setExecutionCode(String codeGenerator) {
		this.executionCode = codeGenerator;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(LocalDate testDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		this.testDate = formatter.format(testDate);
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
			if (extensionRequests.isRequestConfirmed()) {
				this.extraMinute = extensionRequests.getTimeToExtenedInMinute();
				this.testDuration += extraMinute;
			}
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

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public TestStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(TestStatistics statistics) {
		this.statistics = statistics;
		statistics.setTest(this);
	}

	public int getTestDuration() {
		return testDuration;
	}

	public int getNumberOfQuestionInExam() {
		return numberOfQuestionInExam;
	}

	/**
	 * TestCodeGenerator()
	 * 
	 * generated code of four characters and comprised of numbers and letters.
	 * 
	 * @return four number code
	 */
	public String TestCodeGenerator() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 4;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}
}