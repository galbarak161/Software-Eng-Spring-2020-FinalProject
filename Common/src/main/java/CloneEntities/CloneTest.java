package CloneEntities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import CloneEntities.CloneTest.TestStatus;

public class CloneTest implements Serializable {

	private static final long serialVersionUID = 308680455154663629L;

	public enum ExamType {
		Automated, Manual;
	}

	public enum TestStatus {
		Done, Scheduled, Ongoing, PendingApproval, OngoingRequested, OngoingAnswerd;
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

	public CloneTest(int id, LocalDate testDate, LocalTime testTime, String executionCode, int testDuration,
			ExamType type, int teacherId, CloneExam examToExecute) {
		this.id = id;
		this.testDate = testDate;
		this.testTime = testTime;
		this.executionCode = executionCode;
		this.extraMinute = 0;
		this.testDuration = testDuration;
		this.type = type;
		this.status = TestStatus.Scheduled;
		this.teacherId = teacherId;
		this.examToExecute = examToExecute;
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
	}

	public int getExtraMinute() {
		return extraMinute;
	}

	public void setExtraMinute(int extraMinute) {
		this.extraMinute = extraMinute;
	}

	public TestStatus getStatus() {
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

	public LocalTime getTestTime() {
		return testTime;
	}

	public String getExecutionCode() {
		return executionCode;
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

	@Override
	public String toString() {
		return examToExecute.getExamName() + " " + executionCode;
	}
}
