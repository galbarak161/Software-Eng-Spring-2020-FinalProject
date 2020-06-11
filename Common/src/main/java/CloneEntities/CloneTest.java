package CloneEntities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CloneTest implements Serializable {

	private static final long serialVersionUID = 308680455154663629L;

	public enum ExamType {
		Automated, Manual;
	}

	public enum TestStatus {
		Done, Scheduled, Ongoing, PendingApproval, OngoingRequested, OngoingAnswered;
	}

	private int id;

	private LocalDate testDate;

	private LocalTime testTime;

	private String executionCode;

	private int extraMinute;

	private int testDuration;

	private ExamType type;

	private TestStatus status;

	private int teacherId;

	private CloneExam examToExecute;
	
	private CloneTestStatistics statistics;

	private int numberOfQuestions;

	public CloneTest(int id, LocalDate testDate, LocalTime testTime, String executionCode, int extraMinute, int testDuration,
			ExamType type, TestStatus status, int teacherId, CloneExam examToExecute, int numberOfQuestions, CloneTestStatistics statistics) {
		this.id = id;
		this.testDate = testDate;
		this.testTime = testTime;
		this.executionCode = executionCode;
		this.extraMinute = extraMinute;
		this.testDuration = testDuration;
		this.type = type;
		this.status = status;
		this.teacherId = teacherId;
		this.examToExecute = examToExecute;
		this.statistics = statistics;
		this.numberOfQuestions = numberOfQuestions;
	}

	public CloneTest(LocalDate testDate, LocalTime testTime, int testDuration, ExamType type, int teacherId,
			CloneExam examToExecute) {
		this.testDate = testDate;
		this.testTime = testTime;
		this.testDuration = testDuration;
		this.type = type;
		this.teacherId = teacherId;
		this.examToExecute = examToExecute;
		this.extraMinute = 0;
		this.status = TestStatus.Scheduled;
		this.statistics = null;
	}

	public int getExtraMinute() {
		return extraMinute;
	}

	public void setExtraMinute(int extraMinute) {
		this.extraMinute = extraMinute;
	}

	public String getStatus() {
		return status.toString();
	}

	public TestStatus getStatusEnum() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	
	public LocalDate getTestDate() {
		return testDate;
	}

	public String getTestDateInFormat() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY"); 
		return formatter.format(testDate);
	}

	public LocalTime getTestTime() {
		return testTime;
	}

	public String getExecutionCode() {
		return executionCode;
	}

	public String getCourseName() {
		return examToExecute.getCourseName();
	}

	public String getName() {
		return examToExecute.getExamName();
	}

	public int getTestDuration() {
		return testDuration;
	}

	public ExamType getType() {
		return type;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public CloneExam getExamToExecute() {
		return examToExecute;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}
	
	public CloneTestStatistics getStatistics() {
		return statistics;
	}

	@Override
	public String toString() {
		return examToExecute.getExamName() + " " + executionCode;
	}
}