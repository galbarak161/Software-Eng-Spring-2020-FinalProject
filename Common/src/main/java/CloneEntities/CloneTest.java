package CloneEntities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CloneTest implements Serializable{

	private static final long serialVersionUID = 308680455154663629L;
	
    @Override
    public String toString() {
        return examToExecute.getExamName();
    }

	public enum ExamType {
		Automated, Manual;
	}

	private LocalDate testDate;

	private LocalTime testTime;

	private String executionCode;

	private int extraMinute = 0;
	
	private int testDuration;

	private ExamType type;

	//private TimeExtensionRequest extensionRequests;

	private int teacherId;

	//private List<StudentTest> students;

	private CloneExam examToExecute;

	public CloneTest(LocalDate testDate, LocalTime testTime, String executionCode, int extraMinute, int testDuration,
			ExamType type, int teacherId, CloneExam examToExecute) {
		super();
		this.testDate = testDate;
		this.testTime = testTime;
		this.executionCode = executionCode;
		this.extraMinute = extraMinute;
		this.testDuration = testDuration;
		this.type = type;
		this.teacherId = teacherId;
		this.examToExecute = examToExecute;
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

	public String getExecutionCode() {
		return executionCode;
	}

	public void setExecutionCode(String executionCode) {
		this.executionCode = executionCode;
	}

	public int getExtraMinute() {
		return extraMinute;
	}

	public void setExtraMinute(int extraMinute) {
		this.extraMinute = extraMinute;
	}

	public int getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(int testDuration) {
		this.testDuration = testDuration;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public CloneExam getExamToExecute() {
		return examToExecute;
	}

	public void setExamToExecute(CloneExam examToExecute) {
		this.examToExecute = examToExecute;
	}
	
	
}
