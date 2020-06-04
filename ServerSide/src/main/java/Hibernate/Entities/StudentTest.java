package Hibernate.Entities;


import javax.persistence.*;

@Entity
@Table(name = "StudentTest")
public class StudentTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StudentTestId")
	private int id;

	@Column(name = "grade")
	private int grade;

	// @Column(name = "questionsAnswers")
	// private List<Integer> questionsAnswers; // each integer is between 1..4

	@Column(name = "examCheckNotes")
	private String examCheckNotes;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "studentId")
	private Student student;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "testId")
	private Test test;

	public StudentTest() {
		super();
	}

	public StudentTest(Student student, Test test) {
		super();
		this.grade = -1;
		this.examCheckNotes = "";
		setTest(test);
		setStudent(student);
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
}
