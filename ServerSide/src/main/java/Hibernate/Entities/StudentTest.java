package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.TestStatus;

@Entity
@Table(name = "Student_Test")
public class StudentTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StudentTestId")
	private int id;

	@Column(name = "grade")
	private int grade;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
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

	public StudentTest() {
		answers = new ArrayList<AnswerToQuestion>();
	}

	public StudentTest(Student student, Test test) {
		this.grade = -1;
		this.examCheckNotes = "";
		this.status = StudentTestStatus.Scheduled;
		setTest(test);
		setStudent(student);
		answers = new ArrayList<AnswerToQuestion>();
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

	public CloneStudentTest createClone() {
		CloneStudentTest clone = new CloneStudentTest(id, student.createClone(), test.createClone());
		return clone;
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
}
