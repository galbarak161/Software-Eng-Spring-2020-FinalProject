package Hibernate.Entities;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.ExamType;

@Entity
@Table(name = "Student_Test")
public class StudentTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StudentTestId")
	private int id;

	@Column(name = "grade")
	private int grade;

	@Column(name = "startTime")
	private LocalTime startTime;

	@Column(name = "actualTestDurationInMinutes")
	private int actualTestDurationInMinutes;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "student")
	private List<AnswerToQuestion> answers;

	@Column(name = "examCheckNotes")
	private String examCheckNotes;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "studentId")
	private Student student;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "testId")
	private Test test;

	@Column(name = "status")
	private StudentTestStatus status;

	@Column(name = "copyOfManualTest")
	private Blob copyOfManualTest;
	
	public StudentTest() {
		answers = new ArrayList<AnswerToQuestion>();
	}

	public StudentTest(Student student, Test test) {
		this.grade = -1;
		this.examCheckNotes = "";
		this.status = StudentTestStatus.Scheduled;
		this.startTime = null;
		this.actualTestDurationInMinutes = -1;
		setTest(test);
		setStudent(student);
		this.copyOfManualTest = null;
		answers = new ArrayList<AnswerToQuestion>();
	}

	public CloneStudentTest createClone() {
		CloneStudentTest clone = new CloneStudentTest(id, student.createClone(), test.createClone(), startTime, grade,
				actualTestDurationInMinutes, examCheckNotes, status);
		if(this.getTest().getType()== ExamType.Manual)
			clone.setMaunalTest(convertBlobToString(copyOfManualTest));
		return clone;
	}
	
	

	public int getId() {
		return id;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
		test.getStudents().add(this);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		student.getTests().add(this);
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public String getExamCheckNotes() {
		return examCheckNotes;
	}
	
	public StudentTestStatus getStatus() {
		return status;
	}

	public void setStatus(StudentTestStatus status) {
		this.status = status;
	}

	public List<AnswerToQuestion> getAnswers() {
		return answers;
	}

	public void addAnswer(AnswerToQuestion answer) {
		this.answers.add(answer);
	}

	public int getActualTestDurationInMinutes() {
		return actualTestDurationInMinutes;
	}

	public void setActualTestDurationInMinutes(int actualTestDurationInMinutes) {
		this.actualTestDurationInMinutes = actualTestDurationInMinutes;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Blob getCopyOfManualTest() {
		return copyOfManualTest;
	}

	public void setCopyOfManualTest(Blob copyOfManualTest) {
		this.copyOfManualTest = copyOfManualTest;
	}
	
	private String convertBlobToString (Blob blob) {
		byte[] blobBytes = null;
		try {
			blobBytes = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return new String(blobBytes, java.nio.charset.StandardCharsets.UTF_8);
	}
}
